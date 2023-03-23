package com.example.vi_system.lecturer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.vi_system.R

class LecturerDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_dashboard)
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