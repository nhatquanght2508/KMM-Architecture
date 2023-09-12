package com.dksh.kmm.presentation.ui.features.todos

import cafe.adriel.voyager.core.model.coroutineScope
import com.dksh.kmm.domain.interactors.GetToDoListUseCase
import com.dksh.kmm.presentation.mvi.BaseViewModel
import com.dksh.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class ToDoViewModel(
    private val getToDoUseCase: GetToDoListUseCase,
) : BaseViewModel<ToDoContract.Event, ToDoContract.State, ToDoContract.Effect>() {

    init {
        getTodo()
    }

    override fun createInitialState(): ToDoContract.State =
        ToDoContract.State(todos = ResourceUiState.Idle)

    override fun handleEvent(event: ToDoContract.Event) {
        when (event) {
            ToDoContract.Event.OnTryCheckAgainClick -> getTodo()
            is ToDoContract.Event.OnToDoClick -> setEffect {
                ToDoContract.Effect.NavigateToDetailToDo(
                    event.idTodo
                )
            }

            ToDoContract.Event.OnFavoritesClick -> setEffect { ToDoContract.Effect.NavigateToFavorites }
        }
    }

    private fun getTodo() {
        setState { copy(todos = ResourceUiState.Loading) }
        coroutineScope.launch {
            getToDoUseCase(Unit)
                .onSuccess {
                    setState {
                        copy(
                            todos = if (it.isEmpty())
                                ResourceUiState.Empty
                            else
                                ResourceUiState.Success(it)
                        )
                    }
                }
                .onFailure { setState { copy(todos = ResourceUiState.Error()) } }
        }
    }
}
