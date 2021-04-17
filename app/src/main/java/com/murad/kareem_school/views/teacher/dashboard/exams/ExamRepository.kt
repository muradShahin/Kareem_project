package com.murad.kareem_school.views.teacher.dashboard.exams

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.exams.Exam
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class ExamRepository @Inject constructor(
    val fireStore: FirebaseFirestore
) {

    private val TAG = "ExamRepository"

    private val _submitResult = MutableLiveData<Resource<Boolean>>()
    val submitResult: LiveData<Resource<Boolean>> get() = _submitResult

    fun addExamToDb(exam: Exam) {
        fireStore.collection("exams").add(exam)
            .addOnSuccessListener {
                _submitResult.value = Resource.success(true)
            }.addOnFailureListener {
                _submitResult.value = Resource.error("failed to add exam", false)
                Log.d(TAG, "addExamToDb: ${it.message}")
            }
    }


    fun getAllExams() = flow {

        try {
            emit(Resource.success(fireStore.collection("exams").get().await()))
        } catch (e: Exception) {
            Log.d(TAG, "getAllExams: error getting the data")
        }
    }

    fun getExamResult() = flow {

        try {

            emit(
                Resource.success(
                    fireStore.collection("results")
                        .get().await()
                )
            )

        } catch (e: Exception) {
            Log.d(TAG, "getExamResult: ${e.message}")
        }

    }
}