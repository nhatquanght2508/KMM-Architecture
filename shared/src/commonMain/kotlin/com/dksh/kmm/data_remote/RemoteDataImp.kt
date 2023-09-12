package com.dksh.kmm.data_remote

import com.dksh.kmm.data_remote.model.ApiToDo
import com.dksh.kmm.data_remote.model.mapper.ApiToDoMapper
import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.repository.IRemoteData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RemoteDataImp(
    private val endPoint: String,
    private val httpClient: HttpClient,
    private val apiToDoMapper: ApiToDoMapper,
) : IRemoteData {
    override suspend fun getToDoFromApi(): List<ToDo> =
        apiToDoMapper.map(
            (httpClient.get("$endPoint/todos/").body<List<ApiToDo>>())
        )

    override suspend fun getToDoFromApi(id: Int): ToDo =
        apiToDoMapper.map(
            httpClient.get("$endPoint/todos/$id").body<ApiToDo>()
        )
}