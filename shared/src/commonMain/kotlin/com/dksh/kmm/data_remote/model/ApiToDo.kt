package com.dksh.kmm.data_remote.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiToDoResponse(
    val results: List<ApiToDo>
)

@Serializable
data class ApiToDo(
    val id: Int,
    val userId : Int,
    val title:String,
    val completed: Boolean
)
