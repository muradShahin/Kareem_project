package com.murad.kareem_school.views.teacher.dashboard.my_students

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.user_models.Relation
import com.murad.kareem_school.models.user_models.Teacher
import com.murad.kareem_school.models.user_models.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.internal.ChannelFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class TeacherRepository @Inject constructor(
    val firestore: FirebaseFirestore
) {

    private val TAG = "TeacherRepository"
    private var isExcecuted =false

    suspend fun fetchAllUsers(): QuerySnapshot? {

        return firestore.collection("users").get().await()
    }

    fun addStudentToTeacher(relation: Relation): Flow<Resource<Boolean>> = flow {

        try {
            firestore.collection("relations").add(relation).await()
            emit(Resource.success(true))
        } catch (e: Exception) {
            emit(Resource.error("could not fetch users", false))
        }

    }



    suspend fun getMyStudents(teacher: Teacher): Resource<QuerySnapshot> {

        try {

              return  Resource.success(
                    firestore.collection("relations").whereEqualTo("teacher", teacher).get().await())


        } catch (e: Exception) {
            return Resource.error("could not get data",null)
        }

    }

}