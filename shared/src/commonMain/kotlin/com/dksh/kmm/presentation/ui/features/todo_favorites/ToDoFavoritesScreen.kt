package com.dksh.kmm.presentation.ui.features.todo_favorites

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
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
import com.dksh.kmm.presentation.ui.common.ArrowBackIcon
import com.dksh.kmm.presentation.ui.common.ToDoList
import com.dksh.kmm.presentation.ui.common.state.ManagementResourceUiState
import com.dksh.kmm.presentation.ui.features.todo_detail.ToDoDetailScreen
import kotlinx.coroutines.flow.collectLatest

class ToDoFavoritesScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val toDoFavoritesViewModel = getScreenModel<ToDoFavoritesViewModel>()

        val state by toDoFavoritesViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            toDoFavoritesViewModel.effect.collectLatest { effect ->
                when (effect) {
                    is ToDoFavoritesContract.Effect.NavigateToDetailToDo ->
                        navigator.push(ToDoDetailScreen(effect.idTodo))

                    ToDoFavoritesContract.Effect.BackNavigation -> navigator.pop()
                }
            }
        }

        Scaffold(
            topBar = {
                ActionBar(onBackPressed = {
                    toDoFavoritesViewModel.setEvent(
                        ToDoFavoritesContract.Event.OnBackPressed
                    )
                })
            }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.toDoFavorites,
                successView = { favorites ->
                    ToDoList(
                        todos = favorites,
                        onTodoClick = { idTodo ->
                            toDoFavoritesViewModel.setEvent(
                                ToDoFavoritesContract.Event.OnToDoClick(
                                    idTodo
                                )
                            )
                        }
                    )
                },
                onTryAgain = { toDoFavoritesViewModel.setEvent(ToDoFavoritesContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { toDoFavoritesViewModel.setEvent(ToDoFavoritesContract.Event.OnTryCheckAgainClick) },
                msgCheckAgain = "You don't have favorite to do item yet"
            )
        }
    }
}

@Composable
fun ActionBar(
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = { Text(text = "ToDo Favorites") },
        navigationIcon = { ArrowBackIcon(onBackPressed) }
    )
}