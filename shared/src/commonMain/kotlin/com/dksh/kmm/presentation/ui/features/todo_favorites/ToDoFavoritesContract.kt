package com.dksh.kmm.presentation.ui.features.todo_favorites

import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.presentation.model.ResourceUiState
import com.dksh.kmm.presentation.mvi.UiEffect
import com.dksh.kmm.presentation.mvi.UiEvent
import com.dksh.kmm.presentation.mvi.UiState

interface ToDoFavoritesContract {
    sealed interface Event : UiEvent {
        object OnBackPressed : Event
        object OnTryCheckAgainClick : Event
        data class OnToDoClick(val idToDo: Int) : Event
    }

    data class State(
        val toDoFavorites: ResourceUiState<List<ToDo>>,
    ) : UiState

    sealed interface Effect : UiEffect {
        data class NavigateToDetailToDo(val idTodo: Int) : Effect
        object BackNavigation : Effect

    }
}