package com.example.vi_system.admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.dialogs.AddSubject
import com.example.vi_system.util.Material
import com.example.vi_system.util.Subject
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase

class SubjectFragment : Fragment(), AddSubject.SubjectDialogListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addSubjectFab: FloatingActionButton

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_subject, container, false)
        recyclerView = view.findViewById(R.id.recyclerView4)
        addSubjectFab = view.findViewById(R.id.add_subject_fab)

        addSubjectFab.setOnClickListener {

            val fm: FragmentManager? = fragmentManager
            val dialogFragment : AddSubject = AddSubject()
            // SETS the target fragment for use later when sending results
            // SETS the target fragment for use later when sending results
            dialogFragment.setTargetFragment(this@SubjectFragment, 300)
            dialogFragment.show(fm!!, "dialogFragment")
        }
        return view
    }

    override fun onDialogPositiveClick(subject: Subject) {
        //Firebase Realtime Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("subjects")

        // Push the content data to a new node in the database
        val newContentRef = databaseReference.push()
        newContentRef.setValue(Subject(subject.subjectCode,subject.subjectName,null))
            .addOnCompleteListener {
                Toast.makeText(requireContext(), "saved successfully", Toast.LENGTH_SHORT).show()
            }
    }


}