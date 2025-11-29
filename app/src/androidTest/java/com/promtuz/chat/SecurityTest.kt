package com.promtuz.chat

import com.promtuz.chat.security.StaticSecret
import com.promtuz.core.Crypto
import org.junit.Assert
import org.junit.Test

class SecurityTest {
    private val key =
        "a546e36bf0527c9d3b16154b82465edd62144c0ac1fc5a18506a2244ba449ac4".hexToByteArray()

    @Test
    fun testStaticSecret() {
        Crypto()

        val secret = StaticSecret(key)
        val signingKey = secret.toSigningKey()
        val verifyKey = signingKey.getVerificationKey()

        Assert.assertArrayEquals(
            verifyKey, byteArrayOf(
                6,
                232.toByte(),
                44,
                123,
                79,
                138.toByte(),
                78,
                189.toByte(),
                135.toByte(),
                38,
                239.toByte(),
                31,
                65,
                193.toByte(),
                63,
                164.toByte(),
                32,
                149.toByte(),
                215.toByte(),
                137.toByte(),
                189.toByte(),
                22,
                209.toByte(),
                9,
                95,
                156.toByte(),
                59,
                172.toByte(),
                223.toByte(),
                199.toByte(),
                2,
                52
            )
        )
    }
}