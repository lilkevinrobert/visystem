package com.example.vi_system.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.AddMaterialActivity
import com.example.vi_system.admin.AddStudentActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MaterialFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MaterialFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addMaterialFab: FloatingActionButton
    private lateinit var newMaterialFab: FloatingActionButton
    private lateinit var newMaterialTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_material, container, false)

        recyclerView = view.findViewById(R.id.recyclerView3)
        addMaterialFab = view.findViewById(R.id.add_material_fab)
        newMaterialFab = view.findViewById(R.id.add_new_material)
        newMaterialTextView = view.findViewById(R.id.add_material_text)

        newMaterialFab.visibility = View.GONE
        newMaterialTextView.visibility = View.GONE

        var isFabVisible = false
        addMaterialFab.setOnClickListener {
            if (!isFabVisible) {
                newMaterialFab.show()
                newMaterialTextView.visibility = View.VISIBLE
                isFabVisible = true;
            } else {
                newMaterialFab.hide()
                newMaterialTextView.visibility = View.GONE
                isFabVisible = false;
            }
        }

        newMaterialFab.setOnClickListener {
            val intent: Intent = Intent(requireContext(), AddMaterialActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}