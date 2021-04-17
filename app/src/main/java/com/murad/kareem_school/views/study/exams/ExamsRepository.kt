package com.murad.kareem_school.views.study.exams

import com.google.firebase.firestore.FirebaseFirestore
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.user_models.Student
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class ExamsRepository @Inject constructor(
    val fireStore: FirebaseFirestore
) {


    fun getStudentExams() = flow {

        try {

            emit(Resource.success(fireStore.collection("exams").get().await()))

        } catch (e: Exception) {

        }

    }

    fun getStudentTeachers(student: Student) = flow {

        emit(
            Resource.success(
                fireStore.collection("relations").whereEqualTo("student", student)
                    .get().await()
            )
        )

    }
}