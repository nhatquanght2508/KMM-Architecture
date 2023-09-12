package com.dksh.kmm.domain

import com.dksh.kmm.domain.model.ToDo
import kotlinx.coroutines.flow.Flow

interface IRepository {
    suspend fun getToDos(): List<ToDo>
    suspend fun getToDoFavorites(): Flow<List<ToDo>>
    suspend fun getToDo(id: Int): ToDo
    suspend fun addToDoToFavorites(todo: ToDo)
    suspend fun removeToDoFromFavorite(idTodo: Int)
    suspend fun isToDoFavorite(idTodo: Int): Boolean
}