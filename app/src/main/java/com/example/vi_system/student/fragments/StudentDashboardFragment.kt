package com.example.vi_system.student.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.vi_system.R
import com.example.vi_system.util.Student
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


class StudentDashboardFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var data: Student
    private lateinit var studentName: TextView
    private lateinit var registrationNumber: TextView
    private lateinit var program: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_student_dashboard, container, false)
        studentName = view.findViewById(R.id.studentname)
        registrationNumber = view.findViewById(R.id.registrationNumber)
        registrationNumber = view.findViewById(R.id.registrationNumber)
        mAuth = Firebase.auth

        val email: String? = mAuth.currentUser?.email
        if (email != null) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("users")

            val query = databaseReference.orderByChild("email").equalTo(email)
            query.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        // Retrieve the user data
                        val student = snapshot.getValue(Student::class.java)
                        studentName.text = "Student: ${student?.name}"
                        registrationNumber.text = "Registration Number:  ${student?.userId}"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors
                }
            })








        }


        return view
    }


}