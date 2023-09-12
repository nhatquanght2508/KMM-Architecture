package com.dksh.kmm.repository

import com.dksh.kmm.domain.model.ToDo

interface IRemoteData {
    suspend fun getToDoFromApi(): List<ToDo>
    suspend fun getToDoFromApi(id: Int): ToDo
}