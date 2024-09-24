package com.example.taskcompletiontracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoList()
        }
    }
}


@Composable
fun TodoList() {
    var text by remember { mutableStateOf("") }
    var tasks by remember { mutableStateOf(listOf<Task>()) }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter task") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                if (text.isNotBlank()) {
                    tasks = tasks + Task(text, false)
                    text = ""
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Add Task")
        }
        Button(
            onClick = { tasks = tasks.filter { !it.isCompleted } },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("Clear Completed")
        }
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskCheckedChange = { isCompleted ->
                        tasks = tasks.map {
                            if (it == task) it.copy(isCompleted = isCompleted) else it
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TaskItem(task: Task, onTaskCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = task.description,
            style = if (task.isCompleted) {
                TextStyle(textDecoration = TextDecoration.LineThrough)
            } else {
                TextStyle.Default
            },
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onTaskCheckedChange(it) }
        )
    }
}

data class Task(val description: String, var isCompleted: Boolean)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TodoList()
}