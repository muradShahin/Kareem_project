package com.murad.kareem_school.views.study.filter

import com.google.firebase.firestore.FirebaseFirestore
import com.murad.kareem_school.helpers.Resource
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FilterRepository @Inject constructor(
    val firestore: FirebaseFirestore
) {

    fun getAllLessons() = flow {

        try {

            emit(Resource.success(firestore.collection("lessons").get().await()))


        } catch (e: Exception) {

        }
    }

    fun getLessonBasedOnSubject(subject: String) = flow {

        try {

            emit(
                Resource.success(
                    firestore.collection("lessons")
                        .whereEqualTo("subject", subject)
                        .get()
                        .await()
                )
            )

        } catch (e: Exception) {

        }
    }
}