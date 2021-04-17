package com.murad.kareem_school.views.teacher.dashboard

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.murad.kareem_school.R
import com.murad.kareem_school.views.auth.Auth_Views
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dash_board_teacher.*
import java.lang.Exception

@AndroidEntryPoint
class Dashboard : AppCompatActivity() {

    private  val TAG = "Dashboard"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dash_board_teacher)
        supportActionBar?.hide()

        imageView8.setOnClickListener {
            logOut()
        }
    }

    fun logOut(){

        val firebaseAuth = Firebase.auth

        try {

            if(firebaseAuth.currentUser !=null){
                firebaseAuth.signOut()
                val intent =Intent(this,Auth_Views::class.java)
                startActivity(intent)
                finish()
            }

        }catch (e:Exception){
            Log.d(TAG, "logOut: ${e.message}")
        }
    }


}