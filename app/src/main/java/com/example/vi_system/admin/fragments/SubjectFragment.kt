package com.example.vi_system.admin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SubjectFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addSubjectFab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_subject, container, false)
        recyclerView = view.findViewById(R.id.recyclerView4)
        addSubjectFab = view.findViewById(R.id.add_subject_fab)

        addSubjectFab.setOnClickListener {
            Toast.makeText(requireContext(), "The sloam church", Toast.LENGTH_SHORT).show()
        }
        return view
    }

}