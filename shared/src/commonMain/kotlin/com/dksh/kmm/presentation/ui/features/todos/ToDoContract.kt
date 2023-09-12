package com.dksh.kmm.presentation.ui.features.todos

import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.presentation.model.ResourceUiState
import com.dksh.kmm.presentation.mvi.UiEffect
import com.dksh.kmm.presentation.mvi.UiEvent
import com.dksh.kmm.presentation.mvi.UiState

interface ToDoContract {
    sealed interface Event : UiEvent {
        object OnTryCheckAgainClick : Event
        object OnFavoritesClick : Event
        data class OnToDoClick(val idTodo: Int) : Event
    }

    data class State(
        val todos: ResourceUiState<List<ToDo>>
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailToDo(val idToDo: Int) : Effect
        object NavigateToFavorites : Effect
    }
}


