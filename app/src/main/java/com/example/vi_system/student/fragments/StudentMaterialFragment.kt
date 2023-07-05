package com.example.vi_system.student.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.ViewMaterialActivity
import com.example.vi_system.lecturer.MaterialAdapter
import com.example.vi_system.student.StudentViewPDFActivity
import com.example.vi_system.util.Material
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class StudentMaterialFragment : Fragment(), MaterialAdapter.OnMaterialClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<Material>
    private lateinit var adapter: MaterialAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_student_material, container, false)

        recyclerView = view.findViewById(R.id.student_recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
        dataList = ArrayList()
        adapter = MaterialAdapter(requireContext(), dataList,this)
        recyclerView.adapter = adapter

        //Firebase
        val databaseReference = FirebaseDatabase.getInstance().getReference("materials")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val dataClass = dataSnapshot.getValue(Material::class.java)
                    dataList.add(dataClass!!)
                    Log.d("Lecturer", "onCreate: $dataList")
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(),"Error: ${error.message.toString()}",
                    Toast.LENGTH_LONG).show()
            }

        })

        return view
    }

    override fun onMaterialClickListener(position: Int) {
        val fileUrl: String = dataList[position].fileUrl
        val contents: String = dataList[position].contents
        val intent: Intent = Intent(requireContext(), StudentViewPDFActivity::class.java)
        intent.putExtra("fileUrl",fileUrl)
        intent.putExtra("contents",contents)
        startActivity(intent)
    }

}