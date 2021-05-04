package com.murad.kareem_school.views.teacher.dashboard.exams

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
import com.google.firebase.auth.FirebaseAuth
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.exams.Exam
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.exams_fragment_teacher.*
import javax.inject.Inject

@AndroidEntryPoint
class ExamsTeacher : Fragment() {


    val viewModel: ExamViewModel by viewModels()
    private val TAG = "ExamsTeacher"
    private lateinit var examsAdapter :ExamsAdapter

    @Inject
    lateinit var firebaseAuth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exams_fragment_teacher, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        examsAdapter = ExamsAdapter(this)
        exmas_rec.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter=examsAdapter
        }

        new_exam.setOnClickListener {
            view.findNavController()
                .navigate(ExamsTeacherDirections.actionExamsTeacherToCreateExam())
        }

        getAllExams()
    }

    fun getAllExams() {

        viewModel.getAllExams().observe(viewLifecycleOwner, Observer {
            try {

                if (it.status == Status.SUCCESS) {

                    val querySnapshot = it.data
                    val listOfExams = querySnapshot?.toObjects(Exam::class.java)
                    val listOfMyExams = ArrayList<Exam>()

                    if (listOfExams != null) {
                        for (exam in listOfExams){

                            if(exam.getTeacherEmail() == firebaseAuth.currentUser.email){

                                listOfMyExams.add(exam)
                            }

                        }
                    }

                    examsAdapter.submitList(listOfMyExams)
                }

            } catch (e: Exception) {
                Log.d(TAG, "getAllExams: ${e.message}")
            }

        })
    }


    fun seeResult(exam: Exam){

        try {
            view?.findNavController()?.navigate(ExamsTeacherDirections.actionExamsTeacherToExamResults(exam))
        }catch (e:Exception){
            Log.d(TAG, "seeResult: ${e.message}")
        }
    }




}