package com.example.vi_system.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.vi_system.R

class LoginActivity : AppCompatActivity() {
    private lateinit var openRegisterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Open Register Activity
        openRegisterTextView = findViewById(R.id.openRegisterActivity)
        openRegisterTextView.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }
}