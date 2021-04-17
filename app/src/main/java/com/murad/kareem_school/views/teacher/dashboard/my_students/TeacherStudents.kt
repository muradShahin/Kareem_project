package com.murad.kareem_school.views.teacher.dashboard.my_students

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.user_models.Student
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.teacher_student.*

@AndroidEntryPoint
class TeacherStudents : Fragment() {


    private val viewModel: TeacherStudentViewModel by viewModels()

    private val TAG = "TeacherStudents"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.teacher_student, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        selectMyStudents()
        my_students.setOnClickListener {

            selectMyStudents()

        }

        all_teachers.setOnClickListener {

            selectAllStudents()

        }

        val studentsAdapter = TeacherAdapter(this, object : TeacherAdapter.OnAddClicked {
            override fun onClick(student: Student) {

                viewModel.addStudentToTeacher(student).observe(viewLifecycleOwner, Observer {
                    if (it.status == Status.SUCCESS) {
                        getView()?.let { it1 ->
                            Snackbar.make(
                                it1,
                                "Student Has Been Added To Your Network",
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }

        })

        val myStudentsAdapter = MyStudentsAdapter(this)

        rec_all_students.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        viewModel.students.observe(requireActivity(),
            Observer<Resource<List<Student>>> { result ->
                Log.d(TAG, "onChanged: ${result?.data?.size}")
                if (result?.status == Status.SUCCESS) {
                    rec_all_students.adapter = studentsAdapter
                    hideProgressBar()
                    studentsAdapter.submitList(result.data)
                }


            })

        viewModel.myStudents.observe(requireActivity(), Observer {

            if (it.status == Status.SUCCESS) {
                rec_all_students.adapter = myStudentsAdapter
                myStudentsAdapter.submitList(it.data)
                hideProgressBar()
            }
        })


    }

    private fun selectMyStudents() {

        my_students.setBackgroundResource(R.color.main_blue_dark)
        all_teachers.setBackgroundResource(R.color.white)
        textView2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        textView3.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue_dark))
        viewModel.getMyStudents()
    }

    private fun selectAllStudents() {

        all_teachers.setBackgroundResource(R.color.main_blue_dark)
        my_students.setBackgroundResource(R.color.white)
        textView3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        textView2.setTextColor(ContextCompat.getColor(requireContext(), R.color.main_blue_dark))
        viewModel.getStudentsInMySchool()
        showProgressBar()

    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}