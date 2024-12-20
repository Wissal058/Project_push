package com.example.project1.classes

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.util.Date

data class Task @RequiresApi(Build.VERSION_CODES.O) constructor(
    val id: Int, // Identifiant unique de la tâche
    var title: String, // Titre de la tâche
    var description: String? = null, // Description optionnelle
    var isCompleted: Boolean = false, // Statut d'achèvement
    var priority: TaskPriority = TaskPriority.MEDIUM, // Priorité de la tâche
    var dueDate: LocalDate? = null, // Date d'échéance optionnelle
    @SuppressLint("NewApi") var creationDate: LocalDate = LocalDate.now(), // Date de création par défaut
    var category: Category, // Catégorie de la tâche
    var status: TaskStatus = TaskStatus.Pending, // Statut de la tâche
    var isFavorite: Boolean,
    var isArchived: Boolean
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Task) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
