package com.murad.kareem_school.views.study.student_home

import com.google.firebase.firestore.FirebaseFirestore
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.Teacher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class StudentHomeRepository @Inject constructor(

    val fireStore: FirebaseFirestore
) {


    fun getStudentTeachers(student: Student) = flow {

        emit(
            Resource.success(
                fireStore.collection("relations").whereEqualTo("student", student)
                    .get().await()
            )
        )

    }

    fun getStudentLessons(teacher: Teacher) = flow {

        emit(
            Resource.success(
                fireStore.collection("lessons").whereEqualTo("teacher", teacher).get().await()
            )
        )

    }


    fun getAllStudentsLessons() = flow {

        try {

            emit(Resource.success(fireStore.collection("lessons").get().await()))

        } catch (e: Exception) {

        }
    }

}