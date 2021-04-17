package com.murad.kareem_school.di

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.murad.kareem_school.helpers.Constants.SHARED_PREFERENCE
import com.murad.kareem_school.models.user_models.User
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesFirestore() = Firebase.firestore


    @Singleton
    @Provides
    fun providesFirebaseAuth() = Firebase.auth


    @Singleton
    @Provides
    fun providesFirebaseStorageReference():StorageReference{
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference

        return storageRef
    }


    @Singleton
    @Provides
    fun provideSharedPreference(@ApplicationContext context:Context):SharedPreferences{
        return  context.getSharedPreferences(SHARED_PREFERENCE,Context.MODE_PRIVATE)
    }





}