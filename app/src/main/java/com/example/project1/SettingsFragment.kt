 package com.example.project1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.my_calendr_chat.CardData

 // TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         val view = inflater.inflate(R.layout.setting, container, false)
        val synchronisation = view.findViewById<LinearLayout>(R.id.synchronisation)
        val widgets = view.findViewById<LinearLayout>(R.id.widget)
        val notification = view.findViewById<LinearLayout>(R.id.notification)
        val theme = view.findViewById<LinearLayout>(R.id.themeP)
        val vibration = view.findViewById<LinearLayout>(R.id.tache_sone)
        val version = view.findViewById<LinearLayout>(R.id.version)

        synchronisation.setOnClickListener {
            showRecyclerViewDialog()
        }
        widgets.setOnClickListener {
            showRecyclerViewDialog()
        }
        notification.setOnClickListener {
            showRecyclerViewDialog()
        }
        theme.setOnClickListener {
            showRecyclerViewDialog()
        }
        vibration.setOnClickListener {
            showRecyclerViewDialog()
        }


        return view
    }

    private fun showRecyclerViewDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.payant, null)
        val recyclerViewDialog = dialogView.findViewById<RecyclerView>(R.id.view_pager)

        val cardDataList = listOf(
            CardData("Zainba Arhamni", "Superbe application !", "Très intuitive et bien conçue.", R.drawable.zainab, R.drawable.stars),
            CardData("WiSaal Kamil", "Quelques bugs.", "Mais globalement satisfait.", R.drawable.wissal, R.drawable.stars),
            CardData("Ahmed Ben Moussa", "J'adore l'interface.", "L'expérience utilisateur est géniale.", R.drawable.ahmad, R.drawable.rating)
        )

        recyclerViewDialog.adapter = CardAdapter(cardDataList)
        recyclerViewDialog.layoutManager = LinearLayoutManager(requireContext(),  LinearLayoutManager.HORIZONTAL, false)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Fermer", null)
            .create()

        dialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}