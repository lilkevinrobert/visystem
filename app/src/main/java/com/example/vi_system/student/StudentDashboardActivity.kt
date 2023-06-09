package com.example.vi_system.student

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.vi_system.R
import com.example.vi_system.auth.LoginActivity
import com.example.vi_system.student.fragments.StudentDashboardFragment
import com.example.vi_system.student.fragments.StudentMaterialFragment
import com.example.vi_system.student.fragments.StudentQuizFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class StudentDashboardActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        val isStudentNavToBeSelected: Boolean = intent.getBooleanExtra("studentFragment",false)
        if (savedInstanceState == null){
            if (isStudentNavToBeSelected){
                supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_container_view, StudentDashboardFragment::class.java,null)
                    .commit()
            }else{
                supportFragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, StudentDashboardFragment::class.java,null)
                    .commit()
            }
        }

        toolbar =  findViewById(R.id.toolbar)
        navigationView = findViewById(R.id.nav_view)
        drawerLayout  = findViewById(R.id.drawer_layout)

        val actionBarDrawerToggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.openNavDrawer,
            R.string.closeNavDrawer,
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle.drawerArrowDrawable.color  = resources.getColor(R.color.white)
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setTitle("Closing the App")
            .setMessage("Are you sure you want to close the App?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.dashboard -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                //item.isChecked = true
                fragmentReplacement(StudentDashboardFragment())
            }

            R.id.quiz -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentReplacement(StudentQuizFragment())
            }
            R.id.materials -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentReplacement(StudentMaterialFragment())
            }
/*            R.id.setting ->{
                drawerLayout.closeDrawer(GravityCompat.START)
                //navigate to setting ui
                Toast.makeText(this, "Setting clicked", Toast.LENGTH_SHORT).show()

            }*/
            R.id.logout -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                signOut()
            }
        }
        return true
    }

    private fun fragmentReplacement(fragment: Fragment): Unit {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
    private fun signOut(){
        //sign out the user to login page
        Firebase.auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        Toast.makeText(this,"Logout Successfully", Toast.LENGTH_LONG).show()
        finish()
    }
}