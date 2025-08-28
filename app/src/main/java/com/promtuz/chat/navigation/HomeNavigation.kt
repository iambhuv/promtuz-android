package com.promtuz.chat.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.promtuz.chat.ui.screens.HomeScreen

@Composable
fun HomeNavigation(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(Routes.HomeScreen)
    NavDisplay(
        backStack, entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is Routes.HomeScreen -> {
                    NavEntry(key = key) {
                        HomeScreen()
                    }
                }

                is Routes.ProfileScreen -> {
                    NavEntry(key = key) {
                        Text("Sup Homie")
                    }
                }

                else -> throw RuntimeException("Invalid Screen")
            }
        }
    )
}