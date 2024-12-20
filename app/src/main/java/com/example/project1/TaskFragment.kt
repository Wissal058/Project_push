package com.example.project1

import TaskViewModel
import android.annotation.SuppressLint
import android.content.ClipData.Item
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.adapters.TaskAdapter
import com.example.project1.classes.Category
import com.example.project1.classes.SwipeGesture
import com.example.project1.classes.Task
import com.example.project1.classes.TaskPriority
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaskFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val taskViewModel: TaskViewModel by activityViewModels()
    private val archivedTasks = mutableListOf<Task>()
    private lateinit var adapterTask: TaskAdapter
    private lateinit var searchBar: EditText
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_all_task, container, false)
        val planning_img = view.findViewById<LinearLayout>(R.id.img_vide_task)
        val texteList = view.findViewById<TextView>(R.id.tskList)

        // Initialisation de la RecyclerView
        val recyclerViewTask: RecyclerView = view.findViewById(R.id.recyclerViewTaskAllWissal)
        recyclerViewTask.layoutManager = LinearLayoutManager(requireContext())

        // Initialisation de l'adaptateur
        // Gestion du clic
        adapterTask = TaskAdapter(mutableListOf()) { task ->
            // Crée une instance de TaskDetailFragment avec les détails de la tâche
            val detailFragment = TaskDetailFragment.newInstance(task.id)

            // Navigation vers le fragment de détail
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_layout, detailFragment)
                .addToBackStack(null)
                .commit()
        }

        recyclerViewTask.adapter = adapterTask

        // Observer les modifications dans la liste des tâches depuis le ViewModel
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapterTask.updateTasks(tasks)
            if (tasks.isEmpty()) {
                // Si aucune tâche n'est archivée, afficher un message ou une vue vide
                planning_img.visibility = View.VISIBLE
                texteList.visibility = View.GONE
            }
        }

        // Configuration des gestes de swipe
        val swipeGesture = object : SwipeGesture(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = adapterTask.getTaskAtPosition(position)

                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapterTask.delete(position)
                        taskViewModel.removeTask(task)
                        // Si vous avez une méthode pour supprimer dans le ViewModel
                        Toast.makeText(context, getString(R.string.TaskDeleted)+" : ${task.title}", Toast.LENGTH_SHORT).show()
                    }
                    ItemTouchHelper.RIGHT -> {
                        taskViewModel.archiveTask(task)
                        Toast.makeText(context, getString(R.string.TaskArchived)+" ${task.title}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeGesture)
        itemTouchHelper.attachToRecyclerView(recyclerViewTask)

        // Observer la liste des tâches
        taskViewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            adapterTask.updateTasks(tasks)
        }

        // Initialisation de la barre de recherche
        searchBar = view.findViewById(R.id.search)
        searchBar.addTextChangedListener { text ->
            val query = text.toString().lowercase()
            val filteredList = taskViewModel.tasks.value?.filter {
                it.title.lowercase().contains(query) || (it.description?.lowercase()?.contains(query) ?: false)
            } ?: emptyList()
            adapterTask.updateTasks(filteredList)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TaskFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}