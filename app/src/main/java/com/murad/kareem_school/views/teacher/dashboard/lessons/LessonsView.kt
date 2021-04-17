package com.murad.kareem_school.views.teacher.dashboard.lessons

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.lessons.*
import javax.inject.Inject

@AndroidEntryPoint
class LessonsView : Fragment(R.layout.lessons) {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private val viewModel: LessonsViewModel by viewModels()
    private lateinit var lessonsAdapter: LessonsTeacherAdapter
    private val TAG = "LessonsView"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lessonsAdapter = LessonsTeacherAdapter(this)

        lessons_rec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = lessonsAdapter
        }

        addLesson.setOnClickListener {

            view.findNavController().navigate(LessonsViewDirections.actionLessonsViewToAddLesson2())
        }

        getLessons(getTeacher())
    }

    private fun getTeacher(): Teacher {
        val json = sharedPreferences.getString("UserObject", "")
        return Gson().fromJson(json, Teacher::class.java)
    }

    private fun getLessons(teacher: Teacher) {
        progressBar2.visibility = View.VISIBLE
        viewModel.getTeacherLessons(teacher).observe(viewLifecycleOwner, Observer { result ->
            if (result.status == Status.SUCCESS) {
                progressBar2.visibility = View.GONE

                val lessons = result.data?.toObjects(Lesson::class.java)
                lessonsAdapter.submitList(lessons)
                Log.d(TAG, "getLessons: ${lessons?.size} -- ${result.data?.size()}")
                if (lessons?.size!! > 0) {
                    no_lessons_text.visibility = View.GONE
                    lessons_rec.visibility = View.VISIBLE
                } else {
                    progressBar2.visibility = View.GONE
                    no_lessons_text.visibility = View.VISIBLE
                    lessons_rec.visibility = View.GONE
                }

            }
        })
    }
}