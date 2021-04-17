package com.murad.kareem_school.views.auth.register

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.models.images.ImageResponse
import com.murad.kareem_school.models.user_models.Student

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ViewModelCU @Inject constructor():ViewModel() {
    private  val TAG = "ViewModelCU"


    @Inject
     lateinit var auth:FirebaseAuth

    @Inject
     lateinit var firebaseFirestore: FirebaseFirestore

    @Inject
    lateinit var storageReference:StorageReference



    private val _createdResponse =MutableLiveData<Resource<Boolean>>()

    val createdResponse : LiveData<Resource<Boolean>> get() = _createdResponse

    private val _uploadImageResult = MutableLiveData<Resource<ImageResponse>>()

    val uploadImageResult:LiveData<Resource<ImageResponse>> get()=_uploadImageResult




    fun createUser(student: Student){

        viewModelScope.launch(Dispatchers.IO){

            firebaseFirestore.collection("users").add(student)
                .addOnSuccessListener{

                    signUserInFirebaseAuth(student.getEmail(),student.getPassword())

                }.addOnFailureListener {

                    Log.d(TAG, "createUser: ${it.message}")

                }
        }

    }

    private fun signUserInFirebaseAuth(email: String, password: String) {

        viewModelScope.launch(Dispatchers.IO){

            auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {


                    _createdResponse.value =Resource.success(true)



                }.addOnFailureListener {
                    _createdResponse.value =Resource.error("Couldn't create user",false)

                }
        }

    }

    fun uploadImageToFirebase(imageUri:Uri){


        storageReference = storageReference.child("/profileImages").child("image"+UUID.randomUUID())

        /**
         * first we upload the image using putFile
         */
        storageReference.putFile(imageUri).addOnSuccessListener {

            /**
             * here we retrieve the download Uri for the image we just uploaded
             */
             storageReference.downloadUrl.addOnSuccessListener {

                 _uploadImageResult.value = Resource.success(ImageResponse(true,it.toString()))
             }.addOnFailureListener {

                 _uploadImageResult.value = Resource.error("failed",ImageResponse(false,""))

             }

        }.addOnFailureListener {
            _uploadImageResult.value = Resource.error("failed", ImageResponse(false,""))

        }.addOnProgressListener {

        }



    }


}