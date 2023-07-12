package com.example.vi_system.admin.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.AddLecturerActivity
import com.example.vi_system.admin.adpter.StudentAdapter
import com.example.vi_system.util.Student
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LecturerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LecturerFragment : Fragment(), StudentAdapter.OnStudentClickListener {


    private lateinit var recyclerView: RecyclerView
    private lateinit var addLecturerFab: FloatingActionButton
    private lateinit var newLecturerFab: FloatingActionButton
    private lateinit var newLecturerTextView: TextView
    private lateinit var dataList: ArrayList<Student>
    private lateinit var adapter: StudentAdapter

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

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        dataList = ArrayList()
        adapter = StudentAdapter(requireContext(), dataList,this)
        recyclerView.adapter = adapter

        val databaseReference = FirebaseDatabase.getInstance().getReference("users")

        val query: Query = databaseReference.orderByChild("role").equalTo("lecturer")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    // Retrieve the student data
                    val student = snapshot.getValue(Student::class.java)
                    dataList.add(student!!)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(requireActivity(), databaseError.message.toString(), Toast.LENGTH_LONG).show()
            }
        })

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

    override fun onStudentClickListener(position: Int) {
        val user: Student = dataList[position]
    }

}