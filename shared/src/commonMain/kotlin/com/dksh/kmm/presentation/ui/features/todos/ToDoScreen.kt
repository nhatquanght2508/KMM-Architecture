package com.dksh.kmm.presentation.ui.features.todos

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dksh.kmm.presentation.ui.common.ActionBarIcon
import com.dksh.kmm.presentation.ui.common.ToDoList
import com.dksh.kmm.presentation.ui.common.state.ManagementResourceUiState
import com.dksh.kmm.presentation.ui.features.todo_detail.ToDoDetailScreen
import com.dksh.kmm.presentation.ui.features.todo_favorites.ToDoFavoritesScreen
import kotlinx.coroutines.flow.collectLatest

class ToDoScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val toDoViewModel = getScreenModel<ToDoViewModel>()

        val state by toDoViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            toDoViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is ToDoContract.Effect.NavigateToDetailToDo ->
                        navigator.push(ToDoDetailScreen(effect.idToDo))

                    ToDoContract.Effect.NavigateToFavorites ->
                        navigator.push(ToDoFavoritesScreen())
                }
            }
        }

        Scaffold(
            topBar = { ActionAppBar { toDoViewModel.setEvent(ToDoContract.Event.OnFavoritesClick) } }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.todos,
                successView = { todos ->
                    ToDoList(
                        todos = todos,
                        onTodoClick = { idTodo ->
                            toDoViewModel.setEvent(
                                ToDoContract.Event.OnToDoClick(
                                    idTodo
                                )
                            )
                        }
                    )
                },
                onTryAgain = { toDoViewModel.setEvent(ToDoContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { toDoViewModel.setEvent(ToDoContract.Event.OnTryCheckAgainClick) },
            )
        }
    }
}

@Composable
fun ActionAppBar(
    onClickFavorite: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "KMM Architecture") },
        actions = {
            ActionBarIcon(
                onClick = onClickFavorite,
                icon = Icons.Filled.Favorite
            )
        }
    )
}


