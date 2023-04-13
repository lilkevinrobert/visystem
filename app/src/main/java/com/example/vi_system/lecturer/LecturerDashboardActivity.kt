package com.example.vi_system.lecturer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vi_system.R
import com.example.vi_system.util.Material
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LecturerDashboardActivity : AppCompatActivity(), MaterialAdapter.OnMaterialClickListener {

    private lateinit var newMaterialTextView: TextView
    private lateinit var materialFAB: FloatingActionButton
    private lateinit var addMaterialFAB: FloatingActionButton
    private lateinit var newQuizFAB: FloatingActionButton
    private lateinit var newQuizTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataList: ArrayList<Material>
    private lateinit var adapter: MaterialAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_dashboard)

        newMaterialTextView = findViewById(R.id.add_material_text)
        materialFAB = findViewById(R.id.add_material_fab)
        addMaterialFAB = findViewById(R.id.add_new_material)
        recyclerView = findViewById(R.id.recyclerView)
        newQuizFAB = findViewById(R.id.add_new_quiz)
        newQuizTextView = findViewById(R.id.add_quiz_text)


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        dataList = ArrayList()
        adapter = MaterialAdapter(this, dataList,this)
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
                Toast.makeText(this@LecturerDashboardActivity,"Error: ${error.message.toString()}",
                    Toast.LENGTH_LONG).show()
            }

        })


        //Setting All Fab items gone
        addMaterialFAB.visibility = View.GONE
        newMaterialTextView.visibility = View.GONE
        newQuizFAB.visibility = View.GONE
        newQuizTextView.visibility = View.GONE

        // to check whether sub FAB buttons are visible or not.
        var isFabVisible = false
        materialFAB.setOnClickListener {
            if (!isFabVisible) {
                addMaterialFAB.show()
                newMaterialTextView.visibility = View.VISIBLE
                newQuizFAB.show()
                newQuizTextView.visibility = View.VISIBLE
                isFabVisible = true;
            } else {
                addMaterialFAB.hide()
                newMaterialTextView.visibility = View.GONE
                newQuizFAB.hide()
                newQuizTextView.visibility = View.GONE
                isFabVisible = false;
            }
        }

        addMaterialFAB.setOnClickListener {
            val intent: Intent = Intent(this,UploadMaterialActivity::class.java)
            startActivity(intent)
        }
        
        newQuizFAB.setOnClickListener {
            Toast.makeText(this, "working quiz", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Closing the App")
            .setMessage("Are you sure you want to close the App?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onMaterialClickListener(position: Int) {
        val fileUrl: String = dataList[position].fileUrl
        val intent: Intent = Intent(this,ViewPDFActivity::class.java)
        intent.putExtra("fileUrl",fileUrl)
        startActivity(intent)
    }
}