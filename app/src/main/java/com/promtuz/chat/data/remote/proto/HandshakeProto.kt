package com.promtuz.chat.data.remote.proto

import com.promtuz.chat.data.remote.dto.Bytes
import com.promtuz.chat.utils.serialization.CborEnvelope
import kotlinx.serialization.Serializable

fun bytes(n: Byte) = byteArrayOf(n)

@Serializable
sealed class HandshakeProto : CborEnvelope {
    @Serializable
    data class ClientHello(
        val ipk: Bytes, val epk: Bytes
    ) : HandshakeProto()

    @Serializable
    data class ServerChallenge(
        val epk: Bytes, val ct: Bytes
    ) : HandshakeProto()

    @Serializable
    data class ClientProof(
        val proof: Bytes,
    ) : HandshakeProto()

    @Serializable
    data class ServerAccept(
        val timestamp: ULong
    ) : HandshakeProto()

    @Serializable
    data class ServerReject(
        val reason: String
    ) : HandshakeProto()
}

fun HandshakeProto.expectChallenge(): HandshakeProto.ServerChallenge {
    return this as? HandshakeProto.ServerChallenge
        ?: error("Expected ServerChallenge")
}