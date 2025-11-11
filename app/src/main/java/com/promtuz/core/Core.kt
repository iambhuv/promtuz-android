package com.promtuz.core

class Core {
    companion object {
        init {
            System.loadLibrary("core")
        }
    }

    external fun initLogger()
}