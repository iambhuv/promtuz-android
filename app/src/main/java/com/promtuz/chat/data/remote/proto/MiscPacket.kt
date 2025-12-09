package com.promtuz.chat.data.remote.proto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
object MiscPacket {
    /**
     * When sent to any connected relay, it should respond with [PubAddressRes]
     */
    @Serializable
    @SerialName("PubAddressReq")
    data class PubAddressReq(
        val port: Boolean
    ) : Packet

    /**
     * Contains public connection address of client from the POV of server
     *
     * Used for P2P connections between clients, as its not possible for client to determine its own public address without external source.
     */
    @Serializable
    @SerialName("PubAddressRes")
    data class PubAddressRes(
        val addr: String
    ) : Packet
}