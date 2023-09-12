package com.dksh.kmm.data_cache

import app.cash.sqldelight.coroutines.asFlow
import com.dksh.kmm.data_cache.sqldelight.SharedDatabase
import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.repository.ICacheData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CacheDataImp(
    private val sharedDatabase: SharedDatabase,
) : ICacheData {

    override suspend fun addToDoToFavorite(todo: ToDo) {
        sharedDatabase {
            it.appDatabaseQueries.insertToDoFavorite(
                todo.id.toLong(),
                todo.userId.toLong(),
                todo.title,
                if (todo.completed) 1L else 0L
            )
        }
    }

    override suspend fun removeToDoFromFavorite(idTodo: Int) {
        sharedDatabase {
            it.appDatabaseQueries.removeToDoFavorite(idTodo.toLong())
        }
    }

    override suspend fun getAllToDoFavorites(): Flow<List<ToDo>> =
        sharedDatabase { appDatabase ->
            appDatabase.appDatabaseQueries.selectAllToDoFavorite { id, userId, title, completed ->
                mapFavorite(id, userId, title, completed == 1L)
            }.asFlow()
                .map { it.executeAsList() }
        }

    override suspend fun isToDoFavorite(idTodo: Int): Boolean =
        sharedDatabase {
            it.appDatabaseQueries.selectToDoFavoriteById(idTodo.toLong()).executeAsOne()
        }

    private fun mapFavorite(
        id: Long,
        userId: Long,
        title: String,
        completed: Boolean,
    ): ToDo = ToDo(id.toInt(), userId.toInt(), title, completed)
}