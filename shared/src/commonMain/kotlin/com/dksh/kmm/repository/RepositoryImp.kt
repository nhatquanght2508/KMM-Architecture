package com.dksh.kmm.repository

import com.dksh.kmm.domain.IRepository
import com.dksh.kmm.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

class RepositoryImp(
    private val cacheData: ICacheData,
    private val remoteData: IRemoteData,
) : IRepository {

    override suspend fun getToDos(): List<ToDo> =
        remoteData.getToDoFromApi()

    override suspend fun getToDoFavorites(): Flow<List<ToDo>> =
        cacheData.getAllToDoFavorites()

    override suspend fun getToDo(id: Int): ToDo =
        remoteData.getToDoFromApi(id)

    override suspend fun addToDoToFavorites(todo: ToDo) =
        cacheData.addToDoToFavorite(todo)

        override suspend fun removeToDoFromFavorite(idTodo: Int) =
        cacheData.removeToDoFromFavorite(idTodo)

    override suspend fun isToDoFavorite(idTodo: Int): Boolean =
        cacheData.isToDoFavorite(idTodo)
}