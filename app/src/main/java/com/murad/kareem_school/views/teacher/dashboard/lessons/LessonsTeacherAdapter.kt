package com.murad.kareem_school.views.teacher.dashboard.lessons

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.MediaController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.databinding.LessonsTeacherRowBinding
import com.murad.kareem_school.models.lessons.Lesson
import java.lang.Exception

class LessonsTeacherAdapter(val fragment:LessonsView) :
    ListAdapter<Lesson, LessonsTeacherAdapter.LessonsViewHolder>(LessonDiffUtil()) {

    inner class LessonsViewHolder(val lessonsTeacherRowBinding: LessonsTeacherRowBinding) :
        RecyclerView.ViewHolder(lessonsTeacherRowBinding.root) {

        fun binds(lesson: Lesson) {

            try {
                lessonsTeacherRowBinding.lesson =lesson
                lessonsTeacherRowBinding.videoView.setVideoPath(lesson.getVideoUri())
                lessonsTeacherRowBinding.videoView.setMediaController(MediaController(fragment.requireContext()))
                lessonsTeacherRowBinding.videoView.seekTo(100)

            }catch (e:Exception){

                Log.d("adapterVideoTest", "binds: ${e.message}")
            }



        }
    }

    class LessonDiffUtil : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson) =
            oldItem.getVideoUri() == oldItem.getVideoUri()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val lessonsTeacherRowBinding =
            LessonsTeacherRowBinding.inflate(layoutInflater, parent, false)

        return LessonsViewHolder(lessonsTeacherRowBinding)
    }

    override fun onBindViewHolder(holder: LessonsViewHolder, position: Int) {
        holder.binds(getItem(position))
    }
}