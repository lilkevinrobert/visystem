package com.example.vi_system.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.AddLecturerActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LecturerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LecturerFragment : Fragment() {


    private lateinit var recyclerView: RecyclerView
    private lateinit var addLecturerFab: FloatingActionButton
    private lateinit var newLecturerFab: FloatingActionButton
    private lateinit var newLecturerTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_lecturer, container, false)
        recyclerView = view.findViewById(R.id.recyclerView1)
        addLecturerFab =  view.findViewById(R.id.add_lecturer_fab)
        newLecturerFab = view.findViewById(R.id.add_new_lecturer)
        newLecturerTextView = view.findViewById(R.id.add_lecturer_text)

        newLecturerFab.visibility = View.GONE
        newLecturerTextView.visibility = View.GONE

        // to check whether sub FAB buttons are visible or not.
        var isFabVisible = false
        addLecturerFab.setOnClickListener {
            if (!isFabVisible) {
                newLecturerFab.show()
                newLecturerTextView.visibility = View.VISIBLE
                isFabVisible = true;
            } else {
                newLecturerFab.hide()
                newLecturerTextView.visibility = View.GONE
                isFabVisible = false;
            }
        }

        newLecturerFab.setOnClickListener {
            Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show()
            val intent: Intent = Intent(requireContext(),AddLecturerActivity::class.java)
            startActivity(intent)
        }

        return view

    }

}