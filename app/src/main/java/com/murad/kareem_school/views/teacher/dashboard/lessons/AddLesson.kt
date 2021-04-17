package com.murad.kareem_school.views.teacher.dashboard.lessons

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.lessons.Lesson
import com.murad.kareem_school.models.user_models.Teacher
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_lesson.*
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class AddLesson : Fragment(R.layout.create_lesson) {

    private val viewModel: LessonsViewModel by viewModels()

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    private val TAG = "AddLesson"
    private var vedio_uri: String? = null
    private var image_uri: String? = null
    private var vedio_uri_download: String? = null
    private var image_uri_download: String? = null
    private lateinit var uploadVideoResult: ActivityResultLauncher<Intent>
    private lateinit var uploadImageResult: ActivityResultLauncher<Intent>
    private val subjectsArray = listOf("Arabic", "English", "Since", "Math", "Physics")
    private var grade = listOf("grade", "1", "2", "3", "4", "5")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        @SuppressLint("SetTextI18n")
        uploadVideoResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {

                    Log.d(TAG, "${result.data?.data}")
                    vedio_uri = result.data?.data.toString()
                    upload_video.text = "Video Uploaded"
                    uploadVideoToFirebase()

                }

            }


        uploadImageResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {

                    image_uri = result.data?.data.toString()
                    upload_image.setBackgroundResource(R.color.main_orange_dark)
                    textView4.text = "Image Uploaded"
                    textView4.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                    uploadImageToFirebase()

                }

            }


        lesson_subject.adapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            subjectsArray
        )
            .also {
                it.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
            }


        grade_spinner2.adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, grade)
                .also {
                    it.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
                }


        upload_video.setOnClickListener {
            pickVideoFromGallary()
        }

        upload_image.setOnClickListener {
            pickImageFromGallary()
        }


        submit_btn.setOnClickListener {
            try {
                main_progress.visibility = View.VISIBLE
                if (validateDes())
                    if (validateLessonTitle())
                        if (validateImageUri()!!)
                            if (validateVideoUri()!!) {

                                val lessonSubject = lesson_subject.selectedItem.toString()
                                val grade = grade_spinner2.selectedItem.toString()
                                val lesson = Lesson()
                                lesson.setTitle_lesson(lesson_title.text.toString())
                                lesson.setGrade(grade)
                                lesson.setSubject(lessonSubject)
                                lesson.setImageUri(image_uri_download!!)
                                lesson.setVideoUri(vedio_uri_download!!)
                                lesson.setTeacher(getTeacher()!!)
                                lesson.setDate(Date().time.toString())
                                lesson.setDesc(content.text.toString())

                                viewModel.addLessonToFirebase(lesson).observe(viewLifecycleOwner,
                                    Observer {
                                        if (it.status == Status.SUCCESS) {
                                            main_progress.visibility = View.GONE
                                            Toast.makeText(
                                                requireContext(),
                                                "Lesson has been added successfully",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            getView()?.findNavController()?.navigate(AddLessonDirections.actionAddLesson2ToLessonsView())
                                            getView()?.findNavController()?.popBackStack()

                                        } else {
                                            main_progress.visibility = View.GONE
                                            Toast.makeText(
                                                requireContext(),
                                                "failed to add lesson",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    })

                            } else
                                Toast.makeText(
                                    requireContext(),
                                    "please upload video",
                                    Toast.LENGTH_SHORT
                                ).show()
                        else
                            Toast.makeText(
                                requireContext(),
                                "please upload Image",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                    else
                        Toast.makeText(
                            requireContext(),
                            "please enter lesson title",
                            Toast.LENGTH_SHORT
                        ).show()
                else
                    Toast.makeText(
                        requireContext(),
                        "please enter lesson description",
                        Toast.LENGTH_SHORT
                    ).show()

            } catch (e: Exception) {

            }

        }


    }

    fun uploadVideoToFirebase() {
        progressBar_video.visibility = View.VISIBLE
        viewModel.uploadVideoToFirebase(vedio_uri!!).observe(viewLifecycleOwner, Observer {

            if (it.status == Status.SUCCESS) {
                view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        "Video Uploaded Successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                progressBar_video.visibility = View.GONE
                vedio_uri_download = it.data.toString()
                Log.d(TAG, "uploadVideoToFirebase: ${vedio_uri_download}")
            } else {
                view?.let { it1 ->
                    Snackbar.make(it1, "file uploaded failed", Snackbar.LENGTH_SHORT).show()
                }
                progressBar_video.visibility = View.GONE


            }
        })

    }

    fun uploadImageToFirebase() {
        progressBar_image.visibility = View.VISIBLE
        viewModel.uploadImageToFirebase(image_uri!!).observe(viewLifecycleOwner, Observer {
            if (it.status == Status.SUCCESS) {
                progressBar_image.visibility = View.GONE
                Log.d(TAG, "uploadImageToFirebase: ${it.data.toString()}")
                image_uri_download = it.data.toString()
                view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        "Image Uploaded Successfully",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            } else {
                view?.let { it1 ->
                    Snackbar.make(
                        it1,
                        "Failed to  Uploaded Image ",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                progressBar_video.visibility = View.GONE
            }
        })
    }


    private fun pickVideoFromGallary() {

        val intent =
            Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        uploadVideoResult.launch(intent)

    }

    private fun pickImageFromGallary() {
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        uploadImageResult.launch(intent)
    }

    private fun validateLessonTitle() = lesson_title.text.toString().isNotEmpty()
    private fun validateImageUri() = image_uri_download?.isNotEmpty()
    private fun validateVideoUri() = vedio_uri_download?.isNotEmpty()
    private fun validateDes() = content.text.isNotEmpty()

    private fun getTeacher(): Teacher? {
        val json = sharedPreferences.getString("UserObject", "")
        return Gson().fromJson(json, Teacher::class.java)
    }

}