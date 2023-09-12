package com.dksh.kmm.domain.interactors

import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.interactors.type.BaseUseCaseFlow
import com.dksh.kmm.domain.model.ToDo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetTodoFavoritesUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCaseFlow<Unit, List<ToDo>>(dispatcher) {
    override suspend fun build(param: Unit): Flow<List<ToDo>> = repository.getToDoFavorites()
}