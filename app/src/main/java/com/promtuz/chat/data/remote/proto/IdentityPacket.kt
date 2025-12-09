package com.promtuz.chat.data.remote.proto

import kotlinx.serialization.Serializable

@Serializable
object IdentityPacket {
    /**
     * Yeah it's like the scanner telling the sharer - yo, add me bro
     */
    @Serializable
    class AddMe(
        val ipk: ByteArray,
        val epk: ByteArray,
        val vfk: ByteArray,
        val nickname: String
    ) : Packet

    /**
     * and it's like sharer saying to scanner - aight added you bro
     */
    @Serializable
    class AddedYou(
        /**
         * Timestamp cuz leaving this empty creates weird artifacts
         */
        val timestamp: ULong
    )
}