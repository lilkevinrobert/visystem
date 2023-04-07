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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.admin.AddMaterialActivity
import com.example.vi_system.admin.AddStudentActivity
import com.example.vi_system.admin.ViewMaterialActivity
import com.example.vi_system.lecturer.MaterialAdapter
import com.example.vi_system.lecturer.ViewPDFActivity
import com.example.vi_system.util.Material
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MaterialFragment : Fragment() , MaterialAdapter.OnMaterialClickListener{
    private lateinit var recyclerView: RecyclerView
    private lateinit var addMaterialFab: FloatingActionButton
    private lateinit var newMaterialFab: FloatingActionButton
    private lateinit var newMaterialTextView: TextView
    private lateinit var dataList: ArrayList<Material>
    private lateinit var adapter: MaterialAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_material, container, false)

        recyclerView = view.findViewById(R.id.recyclerView3)
        addMaterialFab = view.findViewById(R.id.add_material_fab)
        newMaterialFab = view.findViewById(R.id.add_new_material)
        newMaterialTextView = view.findViewById(R.id.add_material_text)

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

    override fun onMaterialClickListener(position: Int) {
        val fileUrl: String = dataList[position].fileUrl
        val intent: Intent = Intent(requireContext(), ViewMaterialActivity::class.java)
        intent.putExtra("fileUrl",fileUrl)
        startActivity(intent)
    }

}