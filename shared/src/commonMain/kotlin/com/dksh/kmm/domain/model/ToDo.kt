package com.dksh.kmm.domain.model

data class ToDo(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)