package com.dksh.kmm.presentation.ui.features.todo_detail

import cafe.adriel.voyager.core.model.coroutineScope
import com.dksh.kmm.domain.interactors.GetToDoUseCase
import com.dksh.kmm.domain.interactors.IsToDoFavoriteUseCase
import com.dksh.kmm.domain.interactors.SwitchToDoFavoriteUseCase
import com.dksh.kmm.presentation.mvi.BaseViewModel
import com.dksh.kmm.presentation.model.ResourceUiState
import kotlinx.coroutines.launch

class ToDoDetailViewModel(
    private val getToDoUseCase: GetToDoUseCase,
    private val isToDoFavoriteUseCase: IsToDoFavoriteUseCase,
    private val switchToDoFavoriteUseCase: SwitchToDoFavoriteUseCase,
    private val toDoId: Int,
) : BaseViewModel<ToDoDetailContract.Event, ToDoDetailContract.State, ToDoDetailContract.Effect>() {

    init {
        getToDo(toDoId)
        checkIfIsFavorite(toDoId)
    }

    override fun createInitialState(): ToDoDetailContract.State =
        ToDoDetailContract.State(
            todo = ResourceUiState.Idle,
            isFavorite = ResourceUiState.Idle,
        )

    override fun handleEvent(event: ToDoDetailContract.Event) {
        when (event) {
            ToDoDetailContract.Event.OnFavoriteClick -> switchToDoFavorite(toDoId)
            ToDoDetailContract.Event.OnTryCheckAgainClick -> getToDo(toDoId)
            ToDoDetailContract.Event.OnBackPressed -> setEffect { ToDoDetailContract.Effect.BackNavigation }
        }
    }

    private fun getToDo(toDoId: Int) {
        setState { copy(todo = ResourceUiState.Loading) }
        coroutineScope.launch {
            getToDoUseCase(toDoId)
                .onSuccess { setState { copy(todo = ResourceUiState.Success(it)) } }
                .onFailure { setState { copy(todo = ResourceUiState.Error()) } }
        }
    }

    private fun checkIfIsFavorite(idTodo: Int) {
        setState { copy(isFavorite = ResourceUiState.Loading) }
        coroutineScope.launch {
            isToDoFavoriteUseCase(idTodo)
                .onSuccess { setState { copy(isFavorite = ResourceUiState.Success(it)) } }
                .onFailure { setState { copy(isFavorite = ResourceUiState.Error()) } }
        }
    }

    private fun switchToDoFavorite(idTodo: Int) {
        setState { copy(isFavorite = ResourceUiState.Loading) }
        coroutineScope.launch {
            switchToDoFavoriteUseCase(idTodo)
                .onSuccess {
                    setState { copy(isFavorite = ResourceUiState.Success(it)) }
                    setEffect { ToDoDetailContract.Effect.ToDoAdded }
                }.onFailure { setState { copy(isFavorite = ResourceUiState.Error()) } }
        }
    }
}