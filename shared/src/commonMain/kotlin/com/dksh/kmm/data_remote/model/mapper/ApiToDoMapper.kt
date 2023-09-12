package com.dksh.kmm.data_remote.model.mapper

import com.dksh.kmm.data_remote.model.ApiToDo
import com.dksh.kmm.domain.model.ToDo
import com.dksh.kmm.domain.model.map.Mapper

class ApiToDoMapper : Mapper<ApiToDo, ToDo>() {
    override fun map(model: ApiToDo): ToDo = model.run {
        ToDo(
            id, userId, title, completed
        )
    }

    override fun inverseMap(model: ToDo): ApiToDo {
        TODO("Not yet implemented")
    }
}