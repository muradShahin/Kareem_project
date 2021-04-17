package com.murad.kareem_school.views.study.exams

import androidx.lifecycle.*
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.exams.Exam
import com.murad.kareem_school.models.user_models.Student
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ExamsViewModel @Inject constructor(
    val examsRepository: ExamsRepository
) : ViewModel() {


    private val _studentExams = MutableLiveData<List<Exam>>()
    val studentExams: LiveData<List<Exam>> get() = _studentExams

    fun getStudentExams(teachers: ArrayList<String>) {

        viewModelScope.launch {

            examsRepository.getStudentExams().collect {

                try {

                    if (it.status == Status.SUCCESS) {

                        val exams = it.data?.toObjects(Exam::class.java)
                        val examsList = ArrayList<Exam>()
                        for (exam in exams!!) {

                            if (teachers.contains(exam.getTeacherEmail())) {

                                examsList.add(exam)
                            }

                        }

                        _studentExams.value = examsList


                    }

                } catch (e: Exception) {

                }

            }

        }
    }


    fun getStudentTeachers(student:Student) = examsRepository.getStudentTeachers(student).asLiveData()



}