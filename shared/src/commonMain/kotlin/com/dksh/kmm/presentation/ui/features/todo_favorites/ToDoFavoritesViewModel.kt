package com.dksh.kmm.presentation.ui.features.todo_favorites

import cafe.adriel.voyager.core.model.coroutineScope
import com.dksh.kmm.domain.interactors.GetTodoFavoritesUseCase
import com.dksh.kmm.presentation.mvi.BaseViewModel
import com.dksh.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class ToDoFavoritesViewModel(
    private val getToDoFavoritesUseCase: GetTodoFavoritesUseCase
) : BaseViewModel<ToDoFavoritesContract.Event, ToDoFavoritesContract.State, ToDoFavoritesContract.Effect>() {

    init {
        getTodoFavorites()
    }

    override fun createInitialState(): ToDoFavoritesContract.State =
        ToDoFavoritesContract.State(
            toDoFavorites = ResourceUiState.Idle
        )

    override fun handleEvent(event: ToDoFavoritesContract.Event) {
        when (event) {
            ToDoFavoritesContract.Event.OnTryCheckAgainClick -> getTodoFavorites()
            is ToDoFavoritesContract.Event.OnToDoClick ->
                setEffect { ToDoFavoritesContract.Effect.NavigateToDetailToDo(event.idToDo) }

            ToDoFavoritesContract.Event.OnBackPressed ->
                setEffect { ToDoFavoritesContract.Effect.BackNavigation }
        }
    }

    private fun getTodoFavorites() {
        setState { copy(toDoFavorites = ResourceUiState.Loading) }
        coroutineScope.launch {
            getToDoFavoritesUseCase(Unit).collect {
                it.onSuccess {
                    setState {
                        copy(
                            toDoFavorites =
                            if (it.isEmpty())
                                ResourceUiState.Empty
                            else
                                ResourceUiState.Success(it)
                        )
                    }
                }.onFailure { setState { copy(toDoFavorites = ResourceUiState.Error()) } }
            }
        }
    }
}