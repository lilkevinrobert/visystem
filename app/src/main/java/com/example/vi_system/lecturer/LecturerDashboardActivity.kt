package com.example.vi_system.lecturer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.vi_system.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class LecturerDashboardActivity : AppCompatActivity() {

    private lateinit var newMaterialTextView: TextView
    private lateinit var materialFAB: FloatingActionButton
    private lateinit var addMaterialFAB: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_dashboard)

        newMaterialTextView = findViewById(R.id.add_material_text)
        materialFAB = findViewById(R.id.add_material_fab)
        addMaterialFAB = findViewById(R.id.add_new_material)

        //Setting All Fab items gone
        addMaterialFAB.visibility = View.GONE
        newMaterialTextView.visibility = View.GONE

        // to check whether sub FAB buttons are visible or not.
        var isFabVisible = false
        materialFAB.setOnClickListener {
            if (!isFabVisible) {
                addMaterialFAB.show()
                newMaterialTextView.visibility = View.VISIBLE
                isFabVisible = true;
            } else {
                addMaterialFAB.hide()
                newMaterialTextView.visibility = View.GONE
                isFabVisible = false;
            }
        }

        addMaterialFAB.setOnClickListener {
            val dialogFragment = NewMaterialDialogFragment()
            dialogFragment.show(supportFragmentManager,"upload")
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
}