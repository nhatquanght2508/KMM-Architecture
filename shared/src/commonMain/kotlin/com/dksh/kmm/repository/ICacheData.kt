package com.dksh.kmm.repository

import com.dksh.kmm.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ICacheData {
    suspend fun addToDoToFavorite(todo: ToDo)
    suspend fun removeToDoFromFavorite(idTodo: Int)
    suspend fun getAllToDoFavorites(): Flow<List<ToDo>>
    suspend fun isToDoFavorite(idTodo: Int): Boolean
}