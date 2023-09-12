package com.dksh.kmm.presentation.ui.features.todo_detail

import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.presentation.model.ResourceUiState
import com.dksh.kmm.presentation.mvi.UiEffect
import com.dksh.kmm.presentation.mvi.UiEvent
import com.dksh.kmm.presentation.mvi.UiState

interface ToDoDetailContract {
    sealed interface Event : UiEvent {
        object OnFavoriteClick : Event
        object OnTryCheckAgainClick : Event
        object OnBackPressed : Event
    }

    data class State(
        val todo: ResourceUiState<ToDo>,
        val isFavorite: ResourceUiState<Boolean>,
    ) : UiState

    sealed interface Effect : UiEffect {
        object ToDoAdded : Effect
        object ToDoRemoved : Effect
        object BackNavigation : Effect
    }
}