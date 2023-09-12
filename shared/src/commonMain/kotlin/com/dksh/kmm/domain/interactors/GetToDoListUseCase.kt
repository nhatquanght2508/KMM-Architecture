package com.dksh.kmm.domain.interactors

import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.interactors.type.BaseUseCase
import com.dksh.kmm.domain.model.ToDo
import kotlinx.coroutines.CoroutineDispatcher

class GetToDoListUseCase(
    private val repository: IRepository,
    dispatcher: CoroutineDispatcher,
) : BaseUseCase<Unit, List<ToDo>>(dispatcher){
    override suspend fun block(param: Unit): List<ToDo> = repository.getToDos()
}