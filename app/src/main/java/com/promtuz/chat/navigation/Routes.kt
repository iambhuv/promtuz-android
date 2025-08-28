package com.promtuz.chat.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


object Routes {
    @Serializable
    data object HomeScreen : NavKey

    @Serializable
    data object AuthScreen : NavKey

    @Serializable
    data class ProfileScreen(val userId: String) : NavKey

    @Serializable
    data object SettingScreen : NavKey
}