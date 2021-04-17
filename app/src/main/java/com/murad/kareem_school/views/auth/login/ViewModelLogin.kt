package com.murad.kareem_school.views.auth.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.gson.Gson
import com.murad.kareem_school.helpers.Constants
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.Teacher
import com.murad.kareem_school.models.user_models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ViewModelLogin @Inject constructor():ViewModel() {

    private  val TAG = "ViewModelLogin"
    @Inject
     lateinit var auth: FirebaseAuth

    @Inject
     lateinit var firebaseFirestore: FirebaseFirestore

    @Inject
    lateinit var sharedPreferences: SharedPreferences




    private val _isAuthenticated = MutableLiveData<Resource<Boolean>>()
    val isAuthenticated : LiveData<Resource<Boolean>>  get() = _isAuthenticated

    private val _userInfo = MutableLiveData<Resource<User>>()
    val userInfo :LiveData<Resource<User>> get() = _userInfo

    fun authenticateUser(email:String,password:String){

        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {

                Log.d(TAG, "authenticateUser: success ")
                _isAuthenticated.value = Resource.success(true)

            }.addOnFailureListener {
                Log.d(TAG, "authenticateUser: success ${it.message}")

                _isAuthenticated.value = Resource.error("check your credentials",null)
            }
    }


    fun getUserData(email: String){

       firebaseFirestore.collection("users").get().addOnSuccessListener {

           for (document in it.documents){
               try {
                   
                   if(document.getString("email")?.equals(email)!!){

                       
                       _userInfo.value = Resource.success(document.toObject(User::class.java))
                       if(document.toObject(User::class.java)?.getRole() == "Teacher")
                          saveTeacherInfoToSharedPreference(document.toObject(Teacher::class.java)!!)
                       else
                          saveStudentInfoToSharedPreference(document.toObject(Student::class.java)!!)

                   }
                   
               }catch (e:Exception){

                   Log.d(TAG, "getUserData: Error occuerd while getting user Info")
               }
             
           }

       }


    }


    private fun saveTeacherInfoToSharedPreference(user: Teacher){

        try {
            
            val editor = sharedPreferences.edit()

            val jsonObject = Gson().toJson(user)
            editor.putString("UserObject",jsonObject)
            editor.apply()
            editor.commit()
            
        }catch (e:Exception){

            Log.d(TAG, "saveUserInfoToSharedPreference: Error while storing data in sharedPreference")
        }
    

    }

    private fun saveStudentInfoToSharedPreference(user: Student){

        try {

            val editor = sharedPreferences.edit()

            val jsonObject = Gson().toJson(user)
            editor.putString("UserObject",jsonObject)
            editor.apply()
            editor.commit()

        }catch (e:Exception){

            Log.d(TAG, "saveUserInfoToSharedPreference: Error while storing data in sharedPreference")
        }


    }

}