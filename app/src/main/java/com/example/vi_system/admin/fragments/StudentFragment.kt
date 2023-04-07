package com.example.vi_system.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.AddLecturerActivity
import com.example.vi_system.admin.AddStudentActivity
import com.example.vi_system.student.StudentDashboardActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addStudentFab: FloatingActionButton
    private lateinit var newStudentFab: FloatingActionButton
    private lateinit var newStudentTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_student, container, false)
        recyclerView = view.findViewById(R.id.recyclerView2)
        addStudentFab = view.findViewById(R.id.add_student_fab)
        newStudentFab = view.findViewById(R.id.add_new_student)
        newStudentTextView = view.findViewById(R.id.add_student_text)

        newStudentFab.visibility = View.GONE
        newStudentTextView.visibility = View.GONE

        var isFabVisible = false
        addStudentFab.setOnClickListener {
            if (!isFabVisible) {
                newStudentFab.show()
                newStudentTextView.visibility = View.VISIBLE
                isFabVisible = true;
            } else {
                newStudentFab.hide()
                newStudentTextView.visibility = View.GONE
                isFabVisible = false;
            }
        }

        newStudentFab.setOnClickListener {
            val intent: Intent = Intent(requireContext(), AddStudentActivity::class.java)
            startActivity(intent)
        }
        return view
    }



}