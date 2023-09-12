package com.dksh.kmm

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.dksh.kmm.presentation.ui.features.todos.ToDoScreen
import com.dksh.kmm.presentation.ui.theme.AppTheme

@Composable
internal fun App() = AppTheme {
    Navigator(ToDoScreen())
}

