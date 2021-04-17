package com.murad.kareem_school.views.teacher.dashboard.my_students

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.gson.Gson
import com.murad.kareem_school.helpers.Constants
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.user_models.Relation
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.Teacher
import com.murad.kareem_school.models.user_models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherStudentViewModel @Inject constructor(
    val teacherRepository: TeacherRepository,
    val sharedPreferences: SharedPreferences
) : ViewModel() {


    private val _students = MutableLiveData<Resource<List<Student>>>()
    val students: LiveData<Resource<List<Student>>> get() = _students

    private val _myStudents = MutableLiveData<Resource<List<Student>>>()
    val myStudents: LiveData<Resource<List<Student>>> get() = _myStudents
    private val TAG = "TeacherStudentViewModel"

    init {

        /**
         * to get My students when the viewmodel is first created
         */
    }

    fun getStudentsInMySchool() {

        viewModelScope.launch {

            val querySnapshot = teacherRepository.fetchAllUsers()
            val listOfStudents = ArrayList<Student>()
            for (document in querySnapshot?.documents!!) {

                var student = document.toObject(Student::class.java)
                if (student?.getRole() == "Student") {
                    if (student.getSchool_name() == getUserSchool().getSchool_name()) {
                        listOfStudents.add(student)
                        _students.value = Resource.success(listOfStudents)
                    }
                }

            }
        }


    }

    fun addStudentToTeacher(student: Student): LiveData<Resource<Boolean>> {

        val relation = Relation()
        relation.setStudent(student)
        relation.setTeacher(getUserSchool())


        return teacherRepository.addStudentToTeacher(relation).asLiveData()

    }


    private fun getUserSchool(): Teacher {

        val userJson = sharedPreferences.getString("UserObject", "")
        val teacher = Gson().fromJson(userJson, Teacher::class.java)

        return teacher
    }

    fun getMyStudents(){

        viewModelScope.launch {
            val result = teacherRepository.getMyStudents(getUserSchool())

            val listOfStudents = ArrayList<Student>()
            for (relation in result.data?.documents!!){
                val relationData = relation.toObject(Relation::class.java)
                listOfStudents.add(relationData?.getStudent()!!)

            }
            _myStudents.value = Resource.success(listOfStudents)

        }
    }


}