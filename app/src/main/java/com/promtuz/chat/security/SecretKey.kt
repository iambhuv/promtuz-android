package com.promtuz.chat.security

import com.promtuz.core.Crypto

/**
 * Wrapper around raw sensitive secret key,
 * preventing direct access from the UI
 *
 * idk how much safer it is, it definitely feels safer
 */
class SecretKey(
    private val key: ByteArray, private val crypto: Crypto
) {
    private var used = false

    fun deriveSharedKey(
        publicKeyBytes: ByteArray, salt: String, info: String
    ): ByteArray {
        if (used || key.all { it == 0.toByte() }) {
            throw IllegalStateException("Using used secret key")
        }

        return crypto.deriveSharedKey(
            crypto.diffieHellman(this.key, publicKeyBytes), salt.toByteArray(), info
        ).also {
            used = true
            key.fill(0)
        }
    }
}