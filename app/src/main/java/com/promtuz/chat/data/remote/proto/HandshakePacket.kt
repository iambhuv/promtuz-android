package com.promtuz.chat.data.remote.proto

import com.promtuz.chat.data.remote.dto.Bytes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object HandshakePacket {
    @Serializable
    @SerialName("ClientHello")
    data class ClientHello(
        val ipk: Bytes//, val epk: Bytes
    ) : Packet

    @Serializable
    @SerialName("ServerChallenge")
    data class ServerChallenge(
        val epk: Bytes, val ct: Bytes
    ) : Packet

    @Serializable
    @SerialName("ClientProof")
    data class ClientProof(
        val proof: Bytes,
    ) : Packet

    @Serializable
    @SerialName("ServerAccept")
    data class ServerAccept(
        val timestamp: ULong
    ) : Packet

    @Serializable
    @SerialName("ServerReject")
    data class ServerReject(
        val reason: String
    ) : Packet
}