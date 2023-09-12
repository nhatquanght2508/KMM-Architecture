package com.dksh.kmm.presentation.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dksh.kmm.domain.model.ToDo

@Composable
fun ToDoList(
    todos: List<ToDo>,
    onTodoClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        items(todos) { todo ->
            ToDoItem(
                todo = todo,
                onClick = { onTodoClick(todo.id) }
            )
        }
    }
}