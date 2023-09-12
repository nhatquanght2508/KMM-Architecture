package com.dksh.kmm.presentation.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dksh.kmm.domain.model.ToDo

@Composable
fun ToDoItem(
    todo: ToDo,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.clickable(onClick = onClick)
            .padding(16.dp)
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp))
            .wrapContentWidth()
    )  {
        Text(
            modifier = Modifier.padding(2.dp),
            text = "Title: " + todo.title,
            style = MaterialTheme.typography.h4
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.padding(2.dp),
            text = "UserId: " + todo.userId,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.padding(2.dp),
            text = "ID: " + todo.id,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = Modifier.size(10.dp))
        Text(
            modifier = Modifier.padding(2.dp),
            text = when (todo.completed) {
                true -> "1"
                false -> "0"
            },
            style = MaterialTheme.typography.h5
        )
    }
}

