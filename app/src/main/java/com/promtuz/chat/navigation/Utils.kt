package com.promtuz.chat.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

object Navigate {
    fun base(backStack: NavBackStack, key: NavKey) {
        if (backStack.size >= 2 && backStack[backStack.size - 2] == key) {
            backStack.removeLastOrNull()
        } else if (backStack.last() != key) backStack.add(key)
    }

    fun chat(backStack: NavBackStack, key: AppRoutes.ChatScreen) {
        if (backStack.size >= 2 && backStack[backStack.size - 2] == key) {
            backStack.removeLastOrNull()
        } else if (backStack.last() != key) {
            if (backStack.size >= 2 && backStack[backStack.size - 2] == AppRoutes.App) backStack.removeLastOrNull()
            backStack.add(key)
        }
    }
}