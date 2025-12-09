package com.promtuz.chat.security

class SigningKey(
    @Suppress("Unused") // being used in jni
    private val key: ByteArray
) {
    external fun getVerificationKey(): ByteArray
}