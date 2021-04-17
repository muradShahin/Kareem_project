package com.murad.kareem_school.views.study.exams

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.murad.kareem_school.R
import com.murad.kareem_school.models.exams.Exam
import com.murad.kareem_school.models.exams.Question
import com.murad.kareem_school.models.exams.Result
import com.murad.kareem_school.models.user_models.Student
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.exam_hail.*
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class TakeExam : Fragment() {


    private val answeredQuestions = ArrayList<Question>()
    private val TAG = "TakeExam"
    private lateinit var exam: Exam
    private var questionNumber = 0

    @Inject
    lateinit var fireStore: FirebaseFirestore

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exam_hail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            exam = arguments?.getParcelable("exam")!!


        } catch (e: Exception) {

            Log.d(TAG, "onViewCreated: ${e.message}")
        }

        initExam()

        button3.setOnClickListener {
            if (questionNumber <= 4)
                onNext()
            else {
                submitResult()
            }
        }

    }

    fun initExam() {

        question_txt_ex.text = exam.getListOfAnswers()[questionNumber].getQuestion()

    }


    fun onNext() {

        try {

            if (questionNumber == 4) {

                button3.text = "Finish"
            }

            val actualQuestion = exam.getListOfAnswers()[questionNumber]
            actualQuestion.setAnswer(answer.text.toString())
            answeredQuestions.add(actualQuestion)
            questionNumber++
            answer.text.clear()
            question_txt_ex.text = exam.getListOfAnswers()[questionNumber].getQuestion()
            questions_number.text = "Question number: ${questionNumber + 1}"


        } catch (e: Exception) {
            Log.d(TAG, "onNext: ${e.message}")
        }
    }


    fun submitResult() {

        try {

            exam.setListOfAnswers(answeredQuestions)

            val result = Result()
            result.setExam(exam)
            result.setStudent(getStudent())
            fireStore.collection("results").add(result)
                .addOnSuccessListener {
                    Snackbar.make(
                        requireView(),
                        "Your Result has been submitted",
                        Snackbar.LENGTH_SHORT
                    ).show()

                    view?.findNavController()
                        ?.navigate(TakeExamDirections.actionTakeExamToExamFragment())

                }.addOnFailureListener {
                    Log.d(TAG, "submitResult: ${it.message}")
                }


        } catch (e: Exception) {
            Log.d(TAG, "submitResult: ${e.message}")
        }


    }


    fun getStudent(): Student {

        val json = sharedPreferences.getString("UserObject", "")
        val student = Gson().fromJson(json, Student::class.java)

        return student
    }

}