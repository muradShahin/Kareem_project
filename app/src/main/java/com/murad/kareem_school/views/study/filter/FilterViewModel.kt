package com.murad.kareem_school.views.study.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    val filterRepository: FilterRepository
) : ViewModel() {


    fun getAllLessons() = filterRepository.getAllLessons().asLiveData()

    fun getLessonsOnSubject(subject:String) = filterRepository.getLessonBasedOnSubject(subject).asLiveData()
}