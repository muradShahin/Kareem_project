package com.murad.kareem_school.views.study.student_home

import androidx.lifecycle.*
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    val repository: StudentHomeRepository
) : ViewModel() {


    private val _listOfLessons = MutableLiveData<List<Lesson>>()
    val listOfLessons: LiveData<List<Lesson>> get() = _listOfLessons

    fun studentTeacher(student: Student) = repository.getStudentTeachers(student).asLiveData()

    fun studentLessons(teacher: Teacher) = repository.getStudentLessons(teacher).asLiveData()


    fun getAllLessons(teachersArray: ArrayList<String>) {

        viewModelScope.launch {

            repository.getAllStudentsLessons().collect { result ->
                try {

                    val querySnapshot = result.data

                    val listOfLessons = ArrayList<Lesson>()
                    for (query in querySnapshot?.documents!!) {

                        val lesson = query.toObject(Lesson::class.java)

                        val teacherForLesson = lesson?.getTeacher()?.getEmail()

                        for (email in teachersArray) {

                            if (email == teacherForLesson) {
                                listOfLessons.add(lesson)
                            }
                        }

                    }

                    _listOfLessons.value = listOfLessons

                } catch (e: Exception) {

                }


            }
        }

    }


}