package com.example.vi_system.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import com.example.vi_system.R

class LoginActivity : AppCompatActivity() {
    private lateinit var openRegisterTextView: TextView
    private lateinit var studentId: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setting up
        studentId = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginBtn)

        loginButton.setOnClickListener {
            val _studentId = studentId.text.toString().trim()
            val _password = password.text.toString().trim()

            if (validate(studentId = _studentId, password =_password)){
                Toast.makeText(this, "Input to login authentication", Toast.LENGTH_SHORT).show()
            }

        }


        //Open Register Activity
        openRegisterTextView = findViewById(R.id.openRegisterActivity)
        openRegisterTextView.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

    }


    //Login validations
    private fun validate(studentId: String, password :String): Boolean{

        //when statement
        if (studentId.isNotEmpty() && password.isNotEmpty()){
            return true
        }
        if (studentId.isNotEmpty() || password.isEmpty()){
            toastMessage("Password cannot be blank")
            return false
        }
        if (studentId.isEmpty() || password.isNotEmpty()){
            toastMessage("StudentId and Password cannot be blank")
            return false
        }
        return false
    }

    //Validation failure toast
    private fun toastMessage(message: String){
        Toast.makeText(this, "Validation Failure: $message", Toast.LENGTH_LONG).show()
    }
}