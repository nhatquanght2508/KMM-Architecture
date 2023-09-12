package com.dksh.kmm.domain.interactors

import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.interactors.type.BaseUseCase
import com.dksh.kmm.domain.model.ToDo
import kotlinx.coroutines.CoroutineDispatcher

class GetToDoUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Int, ToDo>(dispatcher){
    override suspend fun block(param: Int): ToDo = repository.getToDo(param)
}