package com.murad.kareem_school.views.study

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.murad.kareem_school.R
import com.murad.kareem_school.views.auth.Auth_Views
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.student_activity.*

@AndroidEntryPoint
class StudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_activity)
        supportActionBar?.hide()

        val navView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.studentHome, R.id.filterFragment,R.id.examFragment
        ).build()

        val navController = Navigation.findNavController(this,R.id.fragment3)
        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        NavigationUI.setupWithNavController(navView,navController)


        imageView7.setOnClickListener {

            loggout()
        }

    }

    fun loggout(){

        val firebaseAuth = Firebase.auth
        if(firebaseAuth.currentUser !=null){
            firebaseAuth.signOut()
            val intent = Intent(this,Auth_Views::class.java)
            startActivity(intent)
            finish()
        }
    }
}