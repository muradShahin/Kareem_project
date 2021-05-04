package com.murad.kareem_school.views.study.exams

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.exams.Exam
import com.murad.kareem_school.models.user_models.Relation
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.exams_fragment.*
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class ExamFragment : Fragment() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val viewModel: ExamsViewModel by viewModels()

    private val TAG = "ExamFragment"

    private lateinit var examsAdapter: ExamsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exams_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        examsAdapter = ExamsAdapter(this)

        exams_rec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = examsAdapter
        }

        viewModel.getStudentTeachers(getStudentObject()!!).observe(viewLifecycleOwner, Observer {
            try {

                if (it.status == Status.SUCCESS) {

                    val relationList = it.data?.toObjects(Relation::class.java)
                    val listOfTeachers = ArrayList<String>()
                    for (relation in relationList!!) {
                        if (relation.getStudent().getEmail() == getStudentObject()?.getEmail()) {
                            listOfTeachers.add(relation.getTeacher().getEmail())
                        }
                    }

                    viewModel.getStudentExams(listOfTeachers)

                    viewModel.studentExams.observe(viewLifecycleOwner, Observer {

                        examsAdapter.submitList(it)
                    })

                    if (relationList.size > 0) {
                        textView8.visibility = View.GONE
                    } else {
                        textView8.visibility = View.VISIBLE

                    }

                }

            } catch (e: Exception) {
                Log.d(TAG, "onViewCreated: ${e.message}")
            }
        })
    }


    fun getStudentObject(): Student? {

        return try {
            val json = sharedPreferences.getString("UserObject", "")
            val student = Gson().fromJson(json, Student::class.java)
            student

        } catch (e: Exception) {
            null
        }
    }


    fun startExam(exam: Exam) {
        try {

            view?.findNavController()
                ?.navigate(ExamFragmentDirections.actionExamFragmentToTakeExam(exam))

        } catch (e: Exception) {
            Log.d(TAG, "startExam: ${e.message}")
        }
    }

}