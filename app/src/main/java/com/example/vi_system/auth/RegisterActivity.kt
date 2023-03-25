package com.example.vi_system.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Visibility
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.vi_system.R
import com.example.vi_system.util.User
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    private lateinit var openLoginActivity: TextView
    private lateinit var username: EditText
    private lateinit var studentId: EditText
    private lateinit var studentName: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Firebase Database setup
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users/")

        //Firebase Authentication setup
        mAuth = Firebase.auth

        username = findViewById(R.id.username)
        studentId = findViewById(R.id.studentId)
        studentName = findViewById(R.id.studentName)
        password = findViewById(R.id.password)
        confirmPassword = findViewById(R.id.cpassword)
        registerButton = findViewById(R.id.registerButton)
        progressBar = findViewById(R.id.registerProgressIndicator)
        openLoginActivity = findViewById(R.id.openLoginActivity)


        registerButton.setOnClickListener {
            val _username = username.text.toString()
            val _studentId = studentId.text.toString()
            val _studentName = studentName.text.toString()
            val _password = password.text.toString()
            val _cpassword = confirmPassword.text.toString()

            if (_cpassword == _password) {
                //converting the student Id to Email for registration
                val email = _studentId.lowercase()
                    .replace("/", "", false)
                    .trim()
                registerStudent(User(_username, _studentId, _studentName, "student","$email@nit.ac.tz"))
            }
        }


        openLoginActivity.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }


    private fun registerStudent(user: User) {
        val userId = user.userId.replace("/", "_", ignoreCase = true)

        //checking  if the userId(StudentID) already exist in the realtime database
        databaseReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    Toast.makeText(this@RegisterActivity, "Student ID already exist", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@RegisterActivity, "Not exist userId", Toast.LENGTH_SHORT).show()
                    databaseReference.child(userId).setValue(user)

                    createUser(password = password.text.toString().trim(), email = user.email)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }

        })

    }

    private fun createUser(password: String, email: String) {
        progressBar.visibility = View.VISIBLE
        registerButton.isEnabled = false

        Log.d("EmailTest", "Email: $email $password")
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //Here successful dialog should be implemented
                    progressBar.visibility = View.INVISIBLE
                    registerButton.isEnabled = true
                    Toast.makeText(
                        this,
                        "Successful Registered, login to continue",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

                if (!task.isSuccessful) {
                    progressBar.visibility = View.INVISIBLE
                    registerButton.isEnabled = true
                    Log.d("FIREBASE", "createUser: ${task.exception?.message.toString()}")
                    Toast.makeText(
                        this,
                        "Failed: ${task.exception?.message.toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}