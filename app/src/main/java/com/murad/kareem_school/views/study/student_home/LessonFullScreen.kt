package com.murad.kareem_school.views.study.student_home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.murad.kareem_school.R
import com.murad.kareem_school.models.lessons.Lesson
import kotlinx.android.synthetic.main.lesson_full_screen.*
import java.lang.Exception

class LessonFullScreen : Fragment() {

    private val TAG = "LessonFullScreen"
    private var lesson: Lesson? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lesson_full_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            lesson = arguments?.getParcelable("lesson")

            initTeacher()
            initLessonTitle()
            initLessonVideo()
            initPhoto()
            initDescription()
        } catch (e: Exception) {
            Log.d(TAG, "onViewCreated: error in getting data from bunddle")
        }


        full_play.setOnClickListener {

            try {

                view.findNavController().navigate(LessonFullScreenDirections.actionLessonFullScreenToFullScreenVideo(lesson?.getVideoUri()!!))

            }catch (e:Exception){

            }
        }
    }

    fun initTeacher(){
        try {

            Glide.with(requireContext())
                .load(lesson?.getTeacher()?.getProfileImage())
                .placeholder(R.drawable.user)
                .into(teacher_image)

            lesson_subject_ii.text = lesson?.getTeacher()?.getName()

        }catch (e:Exception){
            Log.d(TAG, "initTeacher: something went wrong while iniating the teacher image")
        }
    }

    fun initLessonTitle(){
        try {

            lessons_title_ii.text = "Lesson Title : ${lesson?.getTitle_lesson()}"

        }catch (e:Exception){
            Log.d(TAG, "initLessonTitle: coud not load lesson title")
        }
    }
    fun initLessonVideo() {

        try {
            videoView2.setVideoURI(lesson?.getVideoUri()?.toUri())
            videoView2.seekTo(100)
            videoView2.setMediaController(MediaController(requireContext()))
        } catch (e: Exception) {
            Log.d(TAG, "initLessonVideo: error initing the lessons video")
        }

    }

    fun initPhoto() {

        try {

            Glide.with(requireContext())
                .load(lesson?.getImageUri())
                .into(lesson_image)

        } catch (e: Exception) {

            Log.d(TAG, "initPhoto: error initing the image")
        }


    }

    fun initDescription(){

        try {

            desc_txt.text = lesson?.getDesc()

        }catch (e:Exception){
            Log.d(TAG, "initDescription: error while initing the desc text")
        }
    }


}