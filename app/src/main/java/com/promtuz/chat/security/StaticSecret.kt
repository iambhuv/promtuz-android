package com.promtuz.chat.security

/**
 * Wrapper around raw sensitive secret key,
 * preventing direct access from the UI
 *
 * idk how much safer it is, it definitely feels safer
 */
class StaticSecret(
    @Suppress("Unused") // being used in jni
    private val key: ByteArray,
) {
    @Suppress("unused") // being used in jni
    private var used = false

    external fun toSigningKey(): SigningKey

    /**
     * internally sets used = true
     */
    external fun diffieHellman(publicKeyBytes: ByteArray): ByteArray
}