package com.example.project1.classes

sealed class TaskStatus {
    object Pending : TaskStatus() // En attente
    object InProgress : TaskStatus() // En cours
    object Completed : TaskStatus() // Terminée
    data class Custom(val description: String) : TaskStatus() // Statut personnalisé
}
