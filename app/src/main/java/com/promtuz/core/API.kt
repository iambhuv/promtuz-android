package com.promtuz.core

import android.content.Context

object API {
    init {
        System.loadLibrary("core")
        initApi()
    }

    private external fun initApi()

    external fun resolve(context: Context)
}