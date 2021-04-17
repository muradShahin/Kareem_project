package com.murad.kareem_school.views.teacher.dashboard.main_screen

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    val sharedPreferences: SharedPreferences
) : ViewModel() {

    private  var teacherObject:Teacher?=null
    init {

        try {
            val teacherJson = sharedPreferences.getString("UserObject", "")
            teacherObject = Gson().fromJson(teacherJson, Teacher::class.java)
        }catch (e:Exception) {

        }

    }

    fun getTeacherDate(): Teacher? {

       return teacherObject
    }

}