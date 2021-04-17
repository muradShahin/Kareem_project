package com.murad.kareem_school.views.teacher.dashboard.exams


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.exams.Exam
import com.murad.kareem_school.models.exams.Question
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_exam.*
import kotlinx.android.synthetic.main.create_lesson.*
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class CreateExam : Fragment() {

    private var examTitle = ""
    private var listOfQuestions = ArrayList<Question>()
    private var questionCount = 1
    private var fromPrevious = false

    var grade =
        arrayOf("grade", "1", "2", "3", "4", "5")

    private val TAG = "CreateExam"

    val viewModel: ExamViewModel by viewModels()

    var selectedGrade = ""

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_exam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        spinner2.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, grade)

        next_question.setOnClickListener {
            if (validateInput())
                nextQuestion()
            else
                Toast.makeText(requireContext(), "fill the question", Toast.LENGTH_SHORT).show()
        }


        prev_question.setOnClickListener {
            previousQuestion()
        }



        submit.setOnClickListener {

            submitExam()
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                selectedGrade = spinner2.getItemAtPosition(position).toString()
            }

        }


    }

    private fun submitExam() {

        val exam = Exam()
        exam.setListOfAnswers(listOfQuestions)
        exam.setTitle(exam_title.text.toString())
        exam.setTeacherEmail(getTeacherEmail())
        exam.setGrade(selectedGrade)
        exam.setActive(true)

        viewModel.submitExam(exam)

        viewModel.getAddResult.observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {

                try {

                    Snackbar.make(requireView(), "Exam has been submitted", Snackbar.LENGTH_SHORT)
                        .show()

                    view?.findNavController()
                        ?.navigate(CreateExamDirections.actionCreateExamToExamsTeacher())

                } catch (e: Exception) {

                }

            }
        })
    }

    private fun validateInput() = question_txt.text.isNotEmpty()

    fun nextQuestion() {

        saveQuestion()
    }

    fun previousQuestion() {

        try {


            questionCount--
            question_number.text = "Question number : ${questionCount}"
            question_txt.setText(listOfQuestions[questionCount].getQuestion())
            fromPrevious = true

        } catch (e: Exception) {
            Log.d(TAG, "previousQuestion: something went wrong")
        }


    }

    private fun saveQuestion() {

        try {
            if (!fromPrevious) {

                val question = Question()
                question.setQuestion(question_txt.text.toString())
                question.setAnswer("")

                if (questionCount < listOfQuestions.size) {

                    questionCount++
                    question_number.text = "Question number : $questionCount"
                    question_txt.text.clear()

                } else {

                    listOfQuestions.add(question)
                    questionCount++

                    question_number.text = "Question number : $questionCount"
                    question_txt.text.clear()

                }

                if (questionCount == 6) {
                    submit.visibility = View.VISIBLE
                    next_question.visibility = View.GONE
                } else {

                    submit.visibility = View.GONE
                    next_question.visibility = View.VISIBLE
                }
                if (questionCount > 1) {
                    prev_question.visibility = View.VISIBLE
                } else {
                    prev_question.visibility = View.GONE

                }

            } else {

                val question = Question()
                question.setQuestion(question_txt.text.toString())
                question.setAnswer("")

                listOfQuestions.add(questionCount, question)
                questionCount++
                question_number.text = "Question number : $questionCount"
                question_txt.setText(listOfQuestions[questionCount].getQuestion())
                fromPrevious = false
            }

        } catch (e: Exception) {

            Log.d(TAG, "saveQuestion: something went wrong")
        }


    }

    fun getTeacherEmail(): String {

        return try {

            val json = sharedPreferences.getString("UserObject", "")
            val teacher = Gson().fromJson(json, Teacher::class.java)

            teacher.getEmail()

        } catch (e: Exception) {

            "error getting the user email"
        }


    }

}