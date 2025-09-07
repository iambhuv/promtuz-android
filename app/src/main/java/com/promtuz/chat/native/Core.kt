package com.promtuz.chat.native

class Core {
    companion object {
        init {
            System.loadLibrary("core")
        }

        @Volatile
        private var INSTANCE: Core? = null

        fun getInstance(): Core {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Core().also { INSTANCE = it }
            }
        }

    }

    external fun getStaticKey(): ByteArray
}