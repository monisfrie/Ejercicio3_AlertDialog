package com.example.ejercicio3_alertdialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.copy
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.ejercicio3_alertdialog.ui.theme.Ejercicio3_AlertDialogTheme
import com.example.ejercicio3_alertdialog.ui.theme.Priority
import com.example.ejercicio3_alertdialog.ui.theme.Task
import kotlin.collections.addAll

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio3_AlertDialogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(

                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    ToDoListApp()

}

@Composable
fun ToDoListApp() {
    val tasks = remember { mutableStateListOf<Task>() }

    LaunchedEffect(Unit) {

        tasks.addAll(
            listOf(
                Task(1, "Grocery shopping", priority = Priority.HIGH),
                Task(2, "Book doctor appointment"),
                Task(3, "Pay bills", isCompleted = true, priority = Priority.LOW)
            )
        )
    }


    TaskListScreen(tasks)
}

@Composable
fun TaskListScreen(tasks: MutableList<Task>) {
    LazyColumn {
        items(tasks) { task ->
            TaskCard(task,
                onTaskCompletedChange = { taskId, completed ->
                    val index = tasks.indexOfFirst { it.id == taskId }
                    if (index != -1) {
                        tasks[index] = tasks[index].copy(isCompleted = completed)
                    }
                },
                onTaskPriorityChange = { taskId, priority ->
                    val index = tasks.indexOfFirst { it.id == taskId }
                    if (index != -1) {
                        tasks[index] = tasks[index].copy(priority = priority)
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(task: Task, onTaskCompletedChange: (Int, Boolean) -> Unit, onTaskPriorityChange: (Int, Priority) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    Spacer(modifier = Modifier.height(20.dp))
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.title,
                color = if (task.isCompleted) Color.Gray else Color.Black,
                textAlign = TextAlign.Start
            )
            Spacer(Modifier.weight(1f))
            PriorityBadge(priority = task.priority)
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Mark as ${if (task.isCompleted) "pending" else "completed"}") },
                    onClick = {
                        onTaskCompletedChange(task.id, !task.isCompleted)
                        expanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Change priority") },
                    onClick = {
                        val newPriority = when (task.priority) {
                            Priority.HIGH -> Priority.MEDIUM
                            Priority.MEDIUM -> Priority.LOW
                            Priority.LOW -> Priority.HIGH
                        }
                        onTaskPriorityChange(task.id, newPriority)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun PriorityBadge(priority: Priority) {
    val backgroundColor = when (priority) {
        Priority.HIGH -> Color.Red
        Priority.MEDIUM -> Color.Yellow
        Priority.LOW -> Color.Green
    }

    Box(
        modifier = Modifier
            .size(16.dp)
            .background(backgroundColor, shape = RoundedCornerShape(4.dp))
    )
}

