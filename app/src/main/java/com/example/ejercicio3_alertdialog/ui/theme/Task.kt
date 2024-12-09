package com.example.ejercicio3_alertdialog.ui.theme

data class Task(
    val id: Int,
    val title: String,
    var isCompleted: Boolean = false,
    var priority: Priority = Priority.MEDIUM
)

enum class Priority {
    HIGH, MEDIUM, LOW
}
