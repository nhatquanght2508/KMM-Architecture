package com.dksh.kmm.domain.interactors

import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SwitchToDoFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean {
        if (repository.isToDoFavorite(param)) {
            repository.removeToDoFromFavorite(param)
        } else {
            repository.addToDoToFavorites(repository.getToDo(param))
        }
        return repository.isToDoFavorite(param)
    }
}