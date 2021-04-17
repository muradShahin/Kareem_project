package com.murad.kareem_school.views.teacher.dashboard.lessons

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.models.user_models.Teacher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*
import javax.inject.Inject

class LessonsRepository @Inject constructor(

    val firebaseStorage: StorageReference,
    val firestore: FirebaseFirestore
) {

    private val TAG = "LessonsRepository"

    fun uploadVideoToFirebase(videoUri: String) = flow {

        try {

            val randomCode = UUID.randomUUID()
            firebaseStorage.child("videos/$randomCode")
                .putFile(videoUri.toUri()).await()

            emit(
                Resource.success(firebaseStorage.child("videos/$randomCode").downloadUrl.await())
            )

        } catch (e: Exception) {
            Log.d(TAG, "uploadVideoToFirebase: ${e.message}")
        }


    }

    fun uploadImageToFirebase(imageUri: String) = flow {

        try {
            val randomUriCode = UUID.randomUUID()
            firebaseStorage.child("lessons_images/$randomUriCode")
                .putFile(imageUri.toUri()).await()
            emit(Resource.success(firebaseStorage.child("lessons_images/$randomUriCode").downloadUrl.await()))

        } catch (e: Exception) {

            Log.d(TAG, "uploadImageToFirebase: failed")
        }

    }

    fun addLessonToFirebase(lesson: Lesson) = flow {

        try {
            emit(Resource.success(firestore.collection("lessons").add(lesson).await()))

        } catch (e: Exception) {
            Log.d(TAG, "addLesson: failed to add lesson")

        }

    }


    fun getAllLessonsFromDb(teacher:Teacher) = flow {

        try {

            emit(Resource.success(firestore.collection("lessons").whereEqualTo("teacher",teacher).get().await()))

        }catch (e:Exception){

            Log.d(TAG, "getAllLessonsFromDb error: ${e.message}")
        }

    }


}