package com.promtuz.chat.utils.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.net.InetSocketAddress

object InetSocketAddressAsString : KSerializer<InetSocketAddress> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("InetSocketAddress", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: InetSocketAddress) {
        encoder.encodeString("${value.hostString}:${value.port}")
    }

    override fun deserialize(decoder: Decoder): InetSocketAddress {
        val s = decoder.decodeString()
        val (host, port) = s.split(":")
        return InetSocketAddress(host, port.toInt())
    }
}
