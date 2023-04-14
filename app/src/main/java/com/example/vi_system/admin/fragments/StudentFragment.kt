package com.example.vi_system.admin.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.AddLecturerActivity
import com.example.vi_system.admin.AddStudentActivity
import com.example.vi_system.admin.adpter.StudentAdapter
import com.example.vi_system.lecturer.MaterialAdapter
import com.example.vi_system.student.StudentDashboardActivity
import com.example.vi_system.util.Material
import com.example.vi_system.util.Student
import com.example.vi_system.util.User
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class StudentFragment : Fragment(), StudentAdapter.OnStudentClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var addStudentFab: FloatingActionButton
    private lateinit var newStudentFab: FloatingActionButton
    private lateinit var newStudentTextView: TextView
    private lateinit var dataList: ArrayList<User>
    private lateinit var adapter: StudentAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_student, container, false)
        recyclerView = view.findViewById(R.id.recyclerView2)
        addStudentFab = view.findViewById(R.id.add_student_fab)
        newStudentFab = view.findViewById(R.id.add_new_student)
        newStudentTextView = view.findViewById(R.id.add_student_text)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        dataList = ArrayList()
        adapter = StudentAdapter(requireContext(), dataList,this)
        recyclerView.adapter = adapter

        //Firebase
        val databaseReference = FirebaseDatabase.getInstance().getReference("users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val dataClass = dataSnapshot.getValue(User::class.java)
                    dataList.add(dataClass!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Error: ${error.message.toString()}",
                    Toast.LENGTH_LONG).show()
            }
        })

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

    override fun onStudentClickListener(position: Int) {
        val user:User = dataList[position]
        //val intent: Intent = Intent(requireContext(),StudentDetailsActivity::Class.java)
        //intent.putExtra("user",user)
        //startActivity(intent)
    }


}