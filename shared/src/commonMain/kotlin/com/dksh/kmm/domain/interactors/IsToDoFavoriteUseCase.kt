package com.dksh.kmm.domain.interactors

import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.interactors.type.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class IsToDoFavoriteUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, Boolean>(dispatcher) {
    override suspend fun block(param: Int): Boolean = repository.isToDoFavorite(param)
}