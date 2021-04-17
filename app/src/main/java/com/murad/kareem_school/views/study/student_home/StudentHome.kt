package com.murad.kareem_school.views.study.student_home

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
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.models.user_models.Relation
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.student_home.*
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class StudentHome : Fragment() {


    private val studentViewModel: StudentHomeViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val TAG = "StudentHome"

    private var studentTeacher: Teacher? = null

    private lateinit var teachersAdapter: TeachersAdapter
    private lateinit var allLessonsAdapter: AllLessonsAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.student_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teachersAdapter = TeachersAdapter(this)
        teachers_rec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = teachersAdapter
        }

        allLessonsAdapter = AllLessonsAdapter(this)
        lessons_rec.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = allLessonsAdapter
        }




        studentViewModel.studentTeacher(getStudentFromShared())
            .observe(viewLifecycleOwner, Observer {

                if (it.status == Status.SUCCESS) {

                    try {

                        val relations = it.data?.toObjects(Relation::class.java)
                        val teachersList = ArrayList<Teacher>()
                        for (relation in relations!!) {

                            teachersList.add(relation.getTeacher())
                        }

                        val teacher_emails_list = ArrayList<String>()

                        for (teacher in teachersList) {

                            teacher_emails_list.add(teacher.getEmail())
                        }

                        studentViewModel.getAllLessons(teacher_emails_list)

                        teachersAdapter.submitList(teachersList)

                    } catch (e: Exception) {

                    }


                }
            })


        studentViewModel.listOfLessons.observe(viewLifecycleOwner, Observer {

            try {
                allLessonsAdapter.submitList(it)

            } catch (e: Exception) {
                Log.d(TAG, "onViewCreated: something went wrong")
            }
        })


    }


    fun getStudentFromShared(): Student {
        val studentJson = sharedPreferences.getString("UserObject", "")
        val student_object = Gson().fromJson(studentJson, Student::class.java)

        return student_object
    }


    fun goToFullScreen(lesson:Lesson){

        try {

            view?.findNavController()?.navigate(StudentHomeDirections.actionStudentHomeToLessonFullScreen(lesson))

        }catch (e:Exception){
            Log.d(TAG, "goToFullScreen: error while navigating")
        }
    }

}