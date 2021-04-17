package com.murad.kareem_school.views.teacher.dashboard.main_screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.murad.kareem_school.R
import com.murad.kareem_school.models.user_models.Teacher
import com.murad.kareem_school.views.teacher.dashboard.my_students.TeacherStudents
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_main_screen.*
import java.lang.Exception

@AndroidEntryPoint
class MainFragment : Fragment() {


    val viewModel: MainFragmentViewModel by viewModels()
    private var teacher: Teacher? = null

    private val TAG = "MainFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teacher = viewModel.getTeacherDate()

        students.setOnClickListener {
            view.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToTeacherStudents())
        }

        cardView3.setOnClickListener {
            view.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToLessonsView())
        }

        analytic.setOnClickListener {
           view.findNavController().navigate(MainFragmentDirections.actionMainFragmentToExamsTeacher())
        }
        setTeacherProfileImage()
        setTeacherName()
    }

    @SuppressLint("SetTextI18n")
    private fun setTeacherName() {

        try {

            teacher_name.text = "${resources.getString(R.string.hi)} ${teacher?.getName()}"

        } catch (e: Exception) {

            Log.d(TAG, "setTeacherName: failed")
        }

    }

    private fun setTeacherProfileImage() {
        try {

            val profilePic = teacher?.getProfileImage()
            if (profilePic?.isNotEmpty()!!) {
                Glide.with(this)
                    .load(teacher?.getProfileImage())
                    .into(imageView)
            }

        } catch (e: Exception) {

            Log.d(TAG, "setTeacherProfileImage: invalid resources")
        }

    }


}