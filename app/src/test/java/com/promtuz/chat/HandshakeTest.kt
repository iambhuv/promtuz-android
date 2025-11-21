package com.promtuz.chat

import com.promtuz.chat.data.remote.proto.HandshakeProto
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class HandshakeTest {
    private val emptyBytes = ByteArray(32).asList()
    private val filledBytes = ByteArray(32).apply { fill(255.toByte()) }.asList()

    companion object {
        private const val F = 255.toByte()
    }

    private val helloBytes = byteArrayOf(1) + emptyBytes + filledBytes

    @Test
    fun testClientHelloEncode() {
        val hello = HandshakeProto.ClientHello(emptyBytes, filledBytes)

        assertContentEquals(helloBytes, hello.toBytes())
    }

    @Test
    fun testClientHelloDecode() {
        val hello = HandshakeProto.ClientHello(emptyBytes, filledBytes)

        assertEquals(hello, HandshakeProto.fromBytes(helloBytes))
    }
}