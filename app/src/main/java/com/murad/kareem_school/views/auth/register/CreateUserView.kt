package com.murad.kareem_school.views.auth.register

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.*
import com.murad.kareem_school.models.images.ImageResponse
import com.murad.kareem_school.models.user_models.Student
import com.shagi.materialdatepicker.date.DatePickerFragmentDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_user_view.*
import java.lang.Exception

@AndroidEntryPoint
class CreateUserView : Fragment()  {


    private lateinit var viewmodel:ViewModelCU

    var schools =
        arrayOf("school Name", "Hu", "School Pioneers", "Jordan school", "Al-zarqa school")
    var grade =
        arrayOf("grade","1", "2", "3", "4", "5")



    private var SPINNER_POS=0
    private var SPINNER_POS_GRADE=0
    private var SPINNER_POS_CITY=0
    private  val TAG = "CreateUserView"
    private lateinit var selectedUserImage:Uri
    private var profileImageDownloadUri =""

    private lateinit var ROLE :Role


    /**
     *  to handle google signUp
     */
    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result ->

        if(result.resultCode == Activity.RESULT_OK ){

            val selectedImage = result.data
            profile_img.setImageURI(selectedImage?.data)
            selectedUserImage= selectedImage?.data!!

            viewmodel.uploadImageToFirebase(selectedUserImage)

        }
    }

    val cameraShowResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){



    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_user_view,container,false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel=ViewModelProvider(this).get(ViewModelCU::class.java)

        schools_spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,schools)

        grade_spinner.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_dropdown_item,grade)




        schools_spinner.onItemSelectedListener =OnSpinnerSelectItem(object :OnCustomOnSelectListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                SPINNER_POS = position
            }

        })

        grade_spinner.onItemSelectedListener = OnSpinnerSelectItem(object :OnCustomOnSelectListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                SPINNER_POS_GRADE = position
            }

        })


        date_picker.setOnClickListener {

            showDataPicker()
        }

        createBtn.setOnClickListener {
            createUser()
        }

        profile_img.setOnClickListener {
            pickFromCamera()
        }


        viewmodel.uploadImageResult.observe(requireActivity(),
            Observer<Resource<ImageResponse>> {
                
                if(it.status == Status.SUCCESS){

                    Toast.makeText(requireActivity(),"Uploaded",Toast.LENGTH_SHORT).show()
                    Log.d(TAG, "onViewCreated: ${it.data?.downloadUri}")
                    profileImageDownloadUri=it.data?.downloadUri.toString()

                }else{

                    Toast.makeText(requireActivity(),"Failed",Toast.LENGTH_SHORT).show()
                }
                
            })



        showRolePicker()




    }

    private fun showRolePicker() {
        val dialog = Dialog(requireActivity())
        dialog.setContentView(R.layout.role_dialog)

        val button =dialog.findViewById<Button>(R.id.button)
        val button2 = dialog.findViewById<Button>(R.id.button2)

        button.setOnClickListener {

            ROLE=Role.Teacher
            view?.let { it1 -> hideGradePicker(it1) }

            dialog.dismiss()
        }

        button2.setOnClickListener {
            ROLE=Role.STUDENT
            dialog.dismiss()
        }

        dialog.window?.setBackgroundDrawableResource(R.color.transparent)
        dialog.show()
    }

    private fun createUser(){
        val student = Student()

        if(name_field.text.isNotEmpty()){
            student.setName(name_field.text.toString())
             if(validateEmail()){
                 student.setEmail(email_field.text.toString())
                 if(validatePassword()){
                     student.setPassword(passowrd1.text.toString())
                     if(date_picker.text.isNotEmpty()){

                         student.setDataOfBirth(date_picker.text.toString())

                         if(SPINNER_POS != 0 ){

                             student.setSchool_name(schools[SPINNER_POS])
                             if(ROLE == Role.STUDENT) {
                                 student.setGrade(grade[SPINNER_POS])
                                 student.setRole("Student")
                             }else{
                                 student.setGrade("none")
                                 student.setRole("Teacher")
                             }
                             student.setProfileImage(profileImageDownloadUri)
                             startCreatingProcess(student)

                         }else{
                             Toast.makeText(requireContext(),"Please choose a school and grade",Toast.LENGTH_SHORT).show()
                         }

                     }

                 }else{
                     passowrd1.error="doesn't match"
                     passowrd2.error="doesn't match"
                 }
             }else{
                 email_field.error = "require a valid email"
             }

        }else{
            name_field.error = "required"
        }



    }

    private fun startCreatingProcess(student: Student) {
        viewmodel.createUser(student)

        viewmodel.createdResponse.observe(viewLifecycleOwner,
            Observer<Resource<Boolean>> {
                val result = it.status

                if(result == Status.SUCCESS){
                    try {

                        Toast.makeText(requireContext(),"created successfully",Toast.LENGTH_SHORT).show()
                        view?.findNavController()?.navigate(CreateUserViewDirections.actionCreateUserViewToLoginView())

                    }catch (e:Exception){
                        Log.d(TAG, "startCreatingProcess: ")
                    }

                }

            })
    }


    private fun showDataPicker(){

        val dialog = DatePickerFragmentDialog.newInstance({ view, year, monthOfYear, dayOfMonth ->

            date_picker.setText("$year/${monthOfYear+1}/$dayOfMonth")

        }, 2021, 11, 4)

        dialog.show(requireActivity().supportFragmentManager, "tag")

    }

    private fun validateEmail() : Boolean {
        val email = email_field.text.toString()

        if(email.isNotEmpty()){

            return email.contains("@") && email.contains(".com")

        }

        return false

    }

    private fun validatePassword():Boolean{
        val password=passowrd1.text.toString()
        val repeated = passowrd2.text.toString()

        if(password.isNotEmpty() && repeated.isNotEmpty()){

            return password == repeated

        }

        return false
    }


    private fun pickFromCamera(){

        val intent =Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        resultLauncher.launch(intent)
    }

    private fun hideGradePicker(view: View){
        grade_spinner.visibility = View.GONE

    }



}