package com.example.vi_system.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.vi_system.R

class AddMaterialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_material)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Exist. ")
            .setMessage("Are you sure you want to go Back? All changes will be discarded")
            .setPositiveButton("Yes") { _, _ ->
                startActivity(Intent(this,AdminDashboardActivity::class.java))
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }
}