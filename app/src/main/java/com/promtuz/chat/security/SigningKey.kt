package com.promtuz.chat.security

class SigningKey(
    private val key: ByteArray
) {
    external fun getVerificationKey(): ByteArray
}