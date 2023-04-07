package com.example.vi_system.admin

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.vi_system.R
import com.example.vi_system.admin.fragments.DashboardFragment
import com.example.vi_system.admin.fragments.LecturerFragment
import com.example.vi_system.admin.fragments.MaterialFragment
import com.example.vi_system.admin.fragments.StudentFragment
import com.google.android.material.navigation.NavigationView

class AdminDashboardActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view,DashboardFragment::class.java,null)
                .commit()
        }

        toolbar =  findViewById(R.id.toolbar)
        //setSupportActionBar(toolbar)*/

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.top_app_bar,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.signout -> {
                Toast.makeText(this@AdminDashboardActivity, "Sign Out Clicked", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.dashboard -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                //item.isChecked = true
                Toast.makeText(this, "dashboard", Toast.LENGTH_SHORT).show()
                fragmentReplacement(DashboardFragment())
            }

            R.id.lecturers ->{
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentReplacement(LecturerFragment())
            }

            R.id.students -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentReplacement(StudentFragment())
            }
            R.id.materials -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                fragmentReplacement(MaterialFragment())
            }
            R.id.setting ->{
                drawerLayout.closeDrawer(GravityCompat.START)
                //navigate to setting ui
            }
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

    fun signOut(){
        //sign out the user to login page
        Toast.makeText(this, "sign out", Toast.LENGTH_SHORT).show()

    }
}