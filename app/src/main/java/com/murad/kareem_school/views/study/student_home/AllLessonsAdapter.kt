package com.murad.kareem_school.views.study.student_home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.R
import com.murad.kareem_school.databinding.LessonsStudentRowBinding
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.views.study.filter.FilterFragment
import java.lang.Exception


class AllLessonsAdapter(val fragment: Fragment) :
    ListAdapter<Lesson, AllLessonsAdapter.ViewHolderLessons>(LessonsDiffUtil()) {

    private val TAG = "AllLessonsAdapter"

    inner class ViewHolderLessons(val view: LessonsStudentRowBinding) :
        RecyclerView.ViewHolder(view.root) {


        fun binds(lesson: Lesson) {

            view.lesson = lesson

            try {
                view.videoView.setVideoPath(lesson.getVideoUri())
                view.videoView.setMediaController(MediaController(fragment.requireContext()))
                view.videoView.seekTo(100)

            } catch (e: Exception) {

                Log.d(TAG, "binds: could not handle the video")
            }


            view.moreBtn.setOnClickListener {
                if(fragment is StudentHome)
                    fragment.goToFullScreen(lesson)
                else if(fragment is FilterFragment)
                    fragment.goToFullScreen(lesson)
            }
        }
    }


    class LessonsDiffUtil : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson) =
            oldItem.getImageUri() == newItem.getImageUri()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLessons {

        val layoutInflater = LayoutInflater.from(parent.context)
        val studentRowBinding = LessonsStudentRowBinding.inflate(layoutInflater, parent, false)
        return ViewHolderLessons(studentRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolderLessons, position: Int) {

        holder.binds(getItem(position))
    }

}