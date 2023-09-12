package com.dksh.kmm.presentation.ui.features.todo_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.presentation.ui.common.ActionBarIcon
import com.dksh.kmm.presentation.ui.common.ArrowBackIcon
import com.dksh.kmm.presentation.ui.common.state.ManagementResourceUiState
import com.dksh.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.flow.collectLatest
import org.koin.core.parameter.parametersOf

class ToDoDetailScreen(
    private val toDoId: Int,
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val scaffoldState: ScaffoldState = rememberScaffoldState()
        val toDoDetailViewModel =
            getScreenModel<ToDoDetailViewModel> { parametersOf(toDoId) }

        val state by toDoDetailViewModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        LaunchedEffect(key1 = Unit) {
            toDoDetailViewModel.effect.collectLatest { effect ->
                when (effect) {
                    ToDoDetailContract.Effect.ToDoAdded ->
                        scaffoldState.snackbarHostState.showSnackbar("ToDo added to favorites")

                    ToDoDetailContract.Effect.ToDoRemoved ->
                        scaffoldState.snackbarHostState.showSnackbar("ToDo removed from favorites")

                    ToDoDetailContract.Effect.BackNavigation -> navigator.pop()
                }
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ActionBar(
                    todo = state.todo,
                    favorite = state.isFavorite,
                    onActionFavorite = { toDoDetailViewModel.setEvent(ToDoDetailContract.Event.OnFavoriteClick) },
                    onBackPressed = { toDoDetailViewModel.setEvent(ToDoDetailContract.Event.OnBackPressed) }
                )
            }
        ) { padding ->
            ManagementResourceUiState(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                resourceUiState = state.todo,
                successView = { todo ->
                    ToDoDetail(todo)
                },
                onTryAgain = { toDoDetailViewModel.setEvent(ToDoDetailContract.Event.OnTryCheckAgainClick) },
                onCheckAgain = { toDoDetailViewModel.setEvent(ToDoDetailContract.Event.OnTryCheckAgainClick) },
            )
        }
    }
}

@Composable
fun ToDoDetail(todo: ToDo) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Title: " + todo.title,
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "UserId: " + todo.userId,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "ID: " + todo.id,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.padding(2.dp),
            text = when (todo.completed) {
                true -> "1"
                false -> "0"
            },
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun ActionBar(
    todo: ResourceUiState<ToDo>,
    favorite: ResourceUiState<Boolean>,
    onActionFavorite: () -> Unit,
    onBackPressed: () -> Unit,
) {
    TopAppBar(
        title = {
            ManagementResourceUiState(
                resourceUiState = todo,
                successView = { Text(text = it.title) },
                loadingView = { Text(text = "....") },
                onCheckAgain = {},
                onTryAgain = {}
            )
        },
        navigationIcon = { ArrowBackIcon(onBackPressed) },
        actions = {
            ManagementResourceUiState(
                resourceUiState = favorite,
                successView = {
                    ActionBarIcon(
                        onClick = onActionFavorite,
                        icon = if (it) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
                    )
                },
                loadingView = {
                    ActionBarIcon(
                        enabled = false,
                        onClick = {},
                        icon = Icons.Filled.Favorite
                    )
                },
                onCheckAgain = {},
                onTryAgain = {}
            )
        }
    )
}