package com.example.project1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.classes.Task

class TaskAdapter(
    private var tasks: MutableList<Task>, // Liste mutable des tâches
    private val onItemClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // ViewHolder pour gérer chaque élément de la liste
    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val taskImage: ImageView = itemView.findViewById(R.id.task_image)
        val taskName: TextView = itemView.findViewById(R.id.task_name)
        val taskDateLimit: TextView = itemView.findViewById(R.id.task_dateLimit)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.favorite) // Icône Favori
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.task_items, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        //holder.taskImage.setImageResource(task.category.image)
        holder.taskName.text = task.title
        holder.taskDateLimit.text = task.dueDate.toString()
        holder.favoriteIcon.isEnabled = !task.isArchived
        // Mettre à jour l'icône Favori
        holder.favoriteIcon.setImageResource(
            if (task.isFavorite) R.drawable.is_favorite else R.drawable.favorite0
        )

        // Gérer les clics sur l'élément
        holder.itemView.setOnClickListener { onItemClick(task) }

        // Action pour marquer comme favori
        holder.favoriteIcon.setOnClickListener {
            task.isFavorite = !task.isFavorite
            notifyItemChanged(position) // Met à jour uniquement l'élément
        }
        // Initialiser l'état de la CheckBox
        val checkBox = holder.itemView.findViewById<CheckBox>(R.id.task_checkbox)
        checkBox.isChecked = task.isCompleted

        // Gérer les clics sur la CheckBox
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.isCompleted = isChecked
            notifyItemChanged(position)
            if (isChecked) {
                showCompletionDialog(holder.itemView.context, task.title)
            }
        }
        // Gérer les clics sur l'élément
        holder.itemView.setOnClickListener { onItemClick(task) }

        holder.favoriteIcon.setOnClickListener {
            task.isFavorite = !task.isFavorite
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = tasks.size

    // Met à jour la liste des tâches
    fun updateTasks(newTasks: List<Task>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged()
    }


    // Marque une tâche comme archivée
    fun archiveTask(position: Int): Task? {
        return if (position in tasks.indices) {
            val archivedTask = tasks.removeAt(position)
            notifyItemRemoved(position)
            archivedTask
        } else {
            null
        }
    }

    fun removeTaskAtPosition(position: Int) {
        if (position in tasks.indices) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    val currentTasks: List<Task>
        get() = tasks

    // Supprime une tâche
    fun delete(position: Int) {
        if (position in tasks.indices) {
            tasks.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    // Ajoute une tâche à une position spécifique
    fun addItem(position: Int, task: Task) {
        if (position in 0..tasks.size) {
            tasks.add(position, task)
            notifyItemInserted(position)
        }
    }

    ////////// boîte de dialogue
    private fun showCompletionDialog(context: Context, taskTitle: String) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.terminee, null)
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Tâche terminée")
            .setMessage("La tâche \"$taskTitle\" est marquée comme terminée.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }


    // Récupère une tâche à une position donnée
    fun getTaskAtPosition(position: Int): Task = tasks[position]
}
