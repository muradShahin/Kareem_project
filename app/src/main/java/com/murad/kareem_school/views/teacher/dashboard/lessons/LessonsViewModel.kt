package com.murad.kareem_school.views.teacher.dashboard.lessons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LessonsViewModel @Inject constructor(
    val lessonsRepository: LessonsRepository
): ViewModel() {


    fun uploadVideoToFirebase(videoUri:String) = lessonsRepository.uploadVideoToFirebase(videoUri).asLiveData()
    fun uploadImageToFirebase(imageUri:String) = lessonsRepository.uploadImageToFirebase(imageUri).asLiveData()
    fun addLessonToFirebase(lesson:Lesson) = lessonsRepository.addLessonToFirebase(lesson).asLiveData()
    fun getTeacherLessons(teacher: Teacher) = lessonsRepository.getAllLessonsFromDb(teacher).asLiveData()

}