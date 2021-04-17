package com.murad.kareem_school.views.teacher.dashboard.exams

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.exams.Exam
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor (
    val examRepository: ExamRepository
) : ViewModel() {


    val getAddResult: LiveData<Resource<Boolean>> by lazy {
        examRepository.submitResult
    }

    fun submitExam(exam: Exam) {
        examRepository.addExamToDb(exam)
    }

    fun getAllExams() = examRepository.getAllExams().asLiveData()


    fun getExamResults() = examRepository.getExamResult().asLiveData()

}