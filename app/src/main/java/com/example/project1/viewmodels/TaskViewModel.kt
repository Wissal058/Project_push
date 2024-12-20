import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.project1.R
import com.example.project1.classes.Category
import com.example.project1.classes.Task
import com.example.project1.classes.TaskPriority
import java.util.Date
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
class TaskViewModel : ViewModel() {
    val tasks: MutableLiveData<MutableList<Task>> = MutableLiveData(mutableListOf())
    val categories: MutableLiveData<MutableList<Category>> = MutableLiveData(mutableListOf())
    val taskFav: MutableLiveData<MutableList<Task>> = MutableLiveData(mutableListOf())
    val taskArch: MutableLiveData<MutableList<Task>> = MutableLiveData(mutableListOf())

    init {
        categories.value = mutableListOf(
            Category(1,"Work", "Scheduale, Agenda...", R.drawable.work),
            Category(2,"Personal", "Family events...", R.drawable.travel),
            Category(3, "Hobby", "Reading, Skydiving...", R.drawable.study)
        )
        tasks.value = mutableListOf(
            Task(0, "Projet des interfaces", isCompleted = false, priority = TaskPriority.HIGH, category = categories.value!![0], creationDate = LocalDate.of(2024, 12, 1), isFavorite = true, isArchived = false, dueDate = LocalDate.of(2025, 12, 12)),
            Task(1, "Projet des interfaces", isCompleted = true, priority = TaskPriority.HIGH, category = categories.value!![0], creationDate = LocalDate.of(2024, 12, 11), isFavorite = true, isArchived = false, dueDate = LocalDate.of(2025, 12, 14)),
            Task(2, "Projet des interfaces", isCompleted = true, priority = TaskPriority.HIGH, category = categories.value!![0], creationDate = LocalDate.of(2024, 12, 21), isFavorite = true, isArchived = false, dueDate = LocalDate.of(2025, 12, 15)),
            Task(3, "Projet des interfaces", isCompleted = false, priority = TaskPriority.HIGH, category = categories.value!![0], creationDate = LocalDate.of(2024, 12, 31), isFavorite = true, isArchived = false, dueDate = LocalDate.of(2025, 12, 30)),
        )
    }

    fun addTask(task: Task) {
        tasks.value?.add(task)
        tasks.notifyChange()
    }

    fun removeTask(task: Task) {
        tasks.value?.remove(task)
        tasks.notifyChange()
    }

    fun archiveTask(task: Task) {
        tasks.value?.remove(task)
        taskArch.value?.add(task.copy(isArchived = true))
        tasks.notifyChange()
        taskArch.notifyChange()
    }

    fun unarchiveTask(task: Task) {
        // Vérifie si la liste des tâches archivées n'est pas nulle
        val archivedTasks = taskArch.value ?: mutableListOf()
        val mainTasks = tasks.value ?: mutableListOf()

        // Rechercher la tâche dans les archivées
        val archivedTask = archivedTasks.find { it.id == task.id }

        if (archivedTask != null) {
            // Supprimer la tâche de la liste des archivées
            val updatedArchivedTasks = archivedTasks.toMutableList()
            updatedArchivedTasks.remove(archivedTask)
            taskArch.value = updatedArchivedTasks

            // Ajouter une copie désarchivée dans la liste principale
            val updatedMainTasks = mainTasks.toMutableList()
            updatedMainTasks.add(archivedTask.copy(isArchived = false))
            tasks.value = updatedMainTasks

            // Pas besoin de notifyChange ici car les listes sont réassignées
        } else {
            println("Erreur : Tâche introuvable pour désarchivage.")
        }
    }



    fun getTasksSize(): Int {
        return tasks.value?.size ?: 0
    }

    fun getCategorySize(): Int {
        return categories.value?.size ?: 0
    }

    fun markAsFavorite(task: Task) {
        tasks.value?.remove(task) // Supprime de la liste principale
        taskFav.value = taskFav.value?.apply {
            add(task.copy(isFavorite = true))
        } ?: mutableListOf(task.copy(isFavorite = true))
        tasks.notifyChange()
        taskFav.notifyChange()
    }


    fun updateTask(task: Task) {
        when {
            task.isArchived -> {
                taskArch.value?.find { it.id == task.id }?.apply {
                    title = task.title
                    description = task.description
                    isFavorite = task.isFavorite
                    isArchived = task.isArchived
                }
                taskArch.notifyChange()
            }
            task.isFavorite -> {
                taskFav.value?.find { it.id == task.id }?.apply {
                    title = task.title
                    description = task.description
                    isFavorite = task.isFavorite
                    isArchived = task.isArchived
                }
                taskFav.notifyChange()
            }
            else -> {
                tasks.value?.find { it.id == task.id }?.apply {
                    title = task.title
                    description = task.description
                    isFavorite = task.isFavorite
                    isArchived = task.isArchived
                }
                tasks.notifyChange()
            }
        }
    }
    fun addCategory(category: Category) {
        categories.value?.add(category)
        categories.notifyChange()
    }
    fun getTasksByCategory(category: Category): List<Task> {
        return tasks.value?.filter { it.category.title == category.title } ?: emptyList()
    }

    fun getTasksForDate(date: LocalDate): List<Task> {
        return tasks.value?.filter { it.dueDate == date } ?: emptyList()
    }



}

// Extension pour forcer LiveData à notifier les observateurs

fun <T> MutableLiveData<MutableList<T>>.notifyChange() {
    this.value = this.value?.toMutableList() // Crée une nouvelle liste mutable pour forcer la mise à jour
}



