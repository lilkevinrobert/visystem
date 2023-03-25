package com.example.vi_system.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vi_system.R
import com.example.vi_system.admin.AdminDashboardActivity
import com.example.vi_system.lecturer.LecturerDashboardActivity
import com.example.vi_system.student.StudentDashboardActivity
import com.example.vi_system.util.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var openRegisterTextView: TextView
    private lateinit var studentId: EditText
    private lateinit var password: EditText
    private lateinit var loginButton: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    companion object ROLE{
        private const val USER_ID = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //setting up
        studentId = findViewById(R.id.username)
        password = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginBtn)

        //Firebase setup
        mAuth = Firebase.auth
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("users")

        loginButton.setOnClickListener {
            val _studentId = studentId.text.toString().trim()
            val _password = password.text.toString().trim()

            if (validate(studentId = _studentId, password = _password)) {
                //converting the student Id to Email for login
                val email = _studentId.lowercase()
                    .replace("/", "", false)
                    .trim()
                loginUser(
                    email = "$email@nit.ac.tz",
                    password = _password,
                    userId = _studentId.replace("/", "_", ignoreCase = true)
                )
            }
        }

        //Open Register Activity
        openRegisterTextView = findViewById(R.id.openRegisterActivity)
        openRegisterTextView.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth.currentUser
        if(currentUser != null){
            //reload()
            Log.d("EMAIL", "onStart: ${currentUser.email?.trim()}")
            val query: Query = databaseReference.orderByValue().equalTo(currentUser.email?.trim())
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("USER_DETAIL", "onDataChange: ${snapshot.getValue<User>().toString()}")

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

        }
    }

    private fun loginUser(email: String, password: String, userId: String) {
        Log.d("USER_ID", "loginUser: $userId")
        val databaseRef = FirebaseDatabase.getInstance().getReference("users")
        databaseRef.child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user = snapshot.getValue<User>()
                    firebaseLogin(email,password,user)
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@LoginActivity, "Error: ${error.message}", Toast.LENGTH_LONG).show()
                }
            })

    }


    private fun firebaseLogin(email: String, password: String, user: User?){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    /*val user = auth.currentUser
                    updateUI(user)*/
                    when (user?.role) {
                        "student" -> {
                            val intent = Intent(this@LoginActivity, StudentDashboardActivity::class.java)
                            intent.putExtra(USER_ID, user.userId)
                            startActivity(intent)
                            finish()
                        }
                        "lecturer" -> {
                            val intent = Intent(this@LoginActivity,LecturerDashboardActivity::class.java)
                            intent.putExtra(USER_ID, user.userId)
                            startActivity(intent)
                            finish()
                        }
                        "admin" -> {
                            val intent = Intent(this@LoginActivity,AdminDashboardActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else -> {
                            Toast.makeText(this, "Failed to Login, Try again", Toast.LENGTH_SHORT).show()
                        }
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }


    }


    //Login validations
    private fun validate(studentId: String, password: String): Boolean {

        //when statement
        if (studentId.isEmpty() && password.isEmpty()) {
            toastMessage("StudentId and Password cannot be blank")
            return false
        }

        if (studentId.isNotEmpty() && password.isEmpty()) {
            toastMessage("Password cannot be blank")
            return false
        }
        if (studentId.isEmpty() && password.isNotEmpty()) {
            toastMessage("StudentId cannot be blank")
            return false
        }

        if (studentId.isNotEmpty() && password.isNotEmpty()) {
            return true
        }
        return false
    }

    //Validation failure toast
    private fun toastMessage(message: String) {
        Toast.makeText(this, "Validation Failed: $message", Toast.LENGTH_LONG).show()
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