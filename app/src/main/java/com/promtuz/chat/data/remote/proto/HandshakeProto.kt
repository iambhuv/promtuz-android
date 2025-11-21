package com.promtuz.chat.data.remote.proto

import kotlinx.io.bytestring.encodeToByteString
import timber.log.Timber

fun bytes(n: Byte) = byteArrayOf(n)

/**
 * TODO: use flatbuffers i suppose
 */
sealed class HandshakeProto {
    data class ClientHello(
        val ipk: List<Byte>, val epk: List<Byte>
    ) : HandshakeProto()

    data class ServerChallenge(
        val epk: List<Byte>, val ct: List<Byte>
    ) : HandshakeProto()

    data class ClientProof(
        val proof: List<Byte>,
    ) : HandshakeProto()

    data class ServerAccept(
        val timestamp: ULong
    ) : HandshakeProto()

    data class ServerReject(
        val reason: String
    ) : HandshakeProto()

    fun toBytes() = when (this) {
        is ClientHello -> bytes(0x01) + ipk + epk
        is ServerChallenge -> bytes(0x02) + epk + ct
        is ClientProof -> bytes(0x03) + proof
        is ServerAccept -> bytes(0x04) + timestamp.toBytesLE()
        is ServerReject -> bytes(0x05) + reason.encodeToByteString().toByteArray()
    }

    companion object {
        private val log = Timber.tag("HandshakeProto")

        fun fromBytes(buf: ByteArray, throws: Boolean = true): HandshakeProto =
            fromBytes(buf).also { throws } ?: error("Invalid Handshake Message")

        fun fromBytes(buf: ByteArray): HandshakeProto? {
            val tag = buf[0]
            var pos = 1

            try {
                return when (tag) {
                    0x01.toByte() -> {
                        val ipk = buf.copyOfRange(pos, pos + 32).toList()
                        pos += 32
                        val epk = buf.copyOfRange(pos, pos + 32).toList()

                        ClientHello(ipk, epk)
                    }

                    0x02.toByte() -> {
                        val epk = buf.copyOfRange(pos, pos + 32).toList()
                        pos += 32
                        val ct = buf.copyOfRange(pos, pos + 32).toList()

                        ServerChallenge(epk, ct)
                    }

                    0x03.toByte() -> {
                        val proof = buf.copyOfRange(pos, pos + 16).toList()
                        ClientProof(proof)
                    }

                    0x04.toByte() -> {
                        val tsBytes = buf.copyOfRange(pos, pos + 8)
                        val ts = java.nio.ByteBuffer.wrap(tsBytes).long.toULong()
                        ServerAccept(ts)
                    }

                    0x05.toByte() -> {
                        val len = buf[pos].toInt()
                        pos++
                        val reason = buf.copyOfRange(pos, pos + len).decodeToString()
                        ServerReject(reason)
                    }

                    else -> null
                }
            } catch (e: Exception) {
                log.e(e, "Decode Error:")
                return null
            }
        }
    }
}

fun ULong.toBytesLE(): ByteArray {
    val v = this.toLong()
    return ByteArray(8) { i -> (v ushr (i * 8)).toByte() }
}

fun HandshakeProto.expectChallenge(): HandshakeProto.ServerChallenge {
    return this as? HandshakeProto.ServerChallenge
        ?: error("Expected ServerChallenge")
}
