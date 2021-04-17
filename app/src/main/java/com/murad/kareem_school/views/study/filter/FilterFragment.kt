package com.murad.kareem_school.views.study.filter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.views.study.student_home.AllLessonsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.filter_view.*


@AndroidEntryPoint
class FilterFragment : Fragment() {

    private val TAG = "FilterFragment"
    private val subjectsArray =
        listOf("Select Subject", "Arabic", "English", "Since", "Math", "Physics")

    private val viewModel: FilterViewModel by viewModels()

    private var allLessonsAdapter: AllLessonsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.filter_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allLessonsAdapter = AllLessonsAdapter(this)
        lessons_rec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = allLessonsAdapter
        }

        spinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            subjectsArray
        )

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if (position > 0) {

                    getLessons(spinner.getItemAtPosition(position).toString())
                } else {

                    getLessons("")
                }
            }

        }
    }

    private fun getLessons(subject: String) {

        if (subject.isEmpty()) {

            viewModel.getAllLessons().observe(viewLifecycleOwner, Observer {

                if (it.status == Status.SUCCESS) {

                    var querySnapshot = it.data
                    val lessonsList = querySnapshot?.toObjects(Lesson::class.java)
                    allLessonsAdapter?.submitList(lessonsList)
                }
            })

        } else {

            viewModel.getLessonsOnSubject(subject).observe(viewLifecycleOwner, Observer {

                if (it.status == Status.SUCCESS) {

                    var querySnapshot = it.data
                    val lessonsList = querySnapshot?.toObjects(Lesson::class.java)
                    allLessonsAdapter?.submitList(lessonsList)
                }
            })
        }
    }


    fun goToFullScreen(lesson: Lesson) {

        try {

            view?.findNavController()
                ?.navigate(FilterFragmentDirections
                    .actionFilterFragmentToLessonFullScreen(lesson))

        } catch (e: Exception) {
            Log.d(TAG, "goToFullScreen: something went wrong")
        }
    }
}