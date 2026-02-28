package com.example.catalogviewer.presentation.navigation

import androidx.compose.runtime.mutableStateListOf
import androidx.navigation3.runtime.NavKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {
    val backStack = mutableStateListOf<NavKey>(CatalogList)

    fun navigateTo(key: NavKey) {
        backStack.add(key)
    }

    fun navigateBack() {
        if (backStack.size > 1) {
            backStack.removeAt(backStack.lastIndex)
        }
    }
}