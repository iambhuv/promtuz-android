package com.promtuz.chat.data.remote.proto

import com.promtuz.chat.utils.serialization.AppCbor
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToByteArray

@Serializable
sealed interface Packet

/**
 * Uses BigEndian btw
 */
fun u32size(len: Int): ByteArray {
    val size: UInt = len.toUInt()
    val sizeBytes = ByteArray(4)
    sizeBytes[0] = (size shr 24).toByte()
    sizeBytes[1] = (size shr 16).toByte()
    sizeBytes[2] = (size shr 8).toByte()
    sizeBytes[3] = size.toByte()
    return sizeBytes
}

/**
 * Frames packet after encoding in CBOR
 */
fun Packet.pack(): ByteArray {
    @OptIn(ExperimentalSerializationApi::class) val data = AppCbor.instance.encodeToByteArray(this)
    return u32size(data.size) + data
}

//object PacketSer : KSerializer<Packet> {
//    @OptIn(InternalSerializationApi::class)
//    override val descriptor = buildSerialDescriptor("Packet", StructureKind.MAP)
//
//    @OptIn(ExperimentalSerializationApi::class)
//    override fun serialize(encoder: Encoder, value: Packet) {
//        val cbor = encoder as? CborEncoder ?: error("PacketSer only works with CBOR")
//
//        val tag = value::class.simpleName ?: error("Missing simpleName")
//
//        val actualSer = encoder.serializersModule.getContextual(Packet::class)
//            ?: error("No contextual serializer for ${value::class}")
//
//        // Write CBOR map of size 1 manually
//        cbor.beginStructure(descriptor)
//        cbor.encodeString(tag)
//        actualSer.serialize(cbor, value)
//    }
//
//    override fun deserialize(decoder: Decoder): Packet {
//        // decode Map<String, Any> using a loose, polymorphic value serializer
//        val mapSer = MapSerializer(
//            String.serializer(), PolymorphicSerializer(Packet::class)
//        )
//        val map = decoder.decodeSerializableValue(mapSer)
//        val (_, value) = map.entries.first()
//
//        return value
//    }
//}