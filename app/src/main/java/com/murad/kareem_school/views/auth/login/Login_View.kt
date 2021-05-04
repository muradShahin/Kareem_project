package com.murad.kareem_school.views.auth.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.murad.kareem_school.MainActivity
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_view.*
import javax.inject.Inject
import com.murad.kareem_school.helpers.Resource
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.user_models.User
import com.murad.kareem_school.views.study.StudentActivity
import com.murad.kareem_school.views.teacher.dashboard.Dashboard
import java.lang.Exception

@AndroidEntryPoint
class Login_View : Fragment() {

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var firebaseStore: FirebaseFirestore

    lateinit var viewMode: ViewModelLogin

    @Inject
    lateinit var sharedPreferences: SharedPreferences


    private val TAG = "Login_View"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_view, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        viewMode = ViewModelProvider(this).get(ViewModelLogin::class.java)


        signUp.setOnClickListener {
            getView()?.findNavController()
                ?.navigate(Login_ViewDirections.actionLoginViewToCreateUserView())
        }

        login.setOnClickListener {

            progressBar3.visibility = View.VISIBLE
            if (validatedEmail()) {

                if (validatePassword()) {

                    viewMode.authenticateUser(
                        email_input.text.toString(),
                        passwordInput.text.toString()
                    )

                } else {

                    passwordLay.error = "required"
                }


            } else {
                email.error = "valid email required"
            }


        }


        viewMode.isAuthenticated.observe(requireActivity(),
            Observer<Resource<Boolean>> {

                if (it.status == Status.SUCCESS) {

                    viewMode.getUserData(email_input.text.toString())
                } else {
                    email.error = "valid email required"
                    passwordLay.error = "password is incorrect"

                }

            })

        viewMode.userInfo.observe(requireActivity(), Observer<Resource<User>> {

            if (it.status == Status.SUCCESS) {

                try {
                    progressBar3.visibility = View.GONE

                    var intent: Intent = if (it.data?.getRole() == "Teacher") {

                        Intent(requireActivity(), Dashboard::class.java)
                    } else {

                        Intent(requireActivity(), StudentActivity::class.java)

                    }

                    startActivity(intent)
                    requireActivity().finish()

                } catch (e: Exception) {
                    progressBar3.visibility = View.GONE
                    Log.d(TAG, "onViewCreated: error while getting data from firebase")
                }

            }

        })


    }

    private fun validatedEmail(): Boolean {
        val email = email_input.text.toString()
        if (email.isNotEmpty()) {

            if (email.contains('@') && email.contains(".com"))
                return true

            return false
        }
        return false
    }

    private fun validatePassword(): Boolean {
        return passwordInput.text.toString().isNotEmpty()
    }


    override fun onResume() {
        super.onResume()

       /* try {

            val jsonObject = sharedPreferences.getString("UserObject", "")
            val userObject = Gson().fromJson(jsonObject, User::class.java)

            if (auth.currentUser != null) {
                if (userObject?.getRole() == "Student") {
                    val intent = Intent(requireActivity(), StudentActivity::class.java)
                    startActivity(intent)
                } else if(userObject?.getRole()=="Teacher"){
                    val intent = Intent(requireActivity(), Dashboard::class.java)
                    startActivity(intent)
                }
            }

        } catch (e: Exception) {

        }*/


    }


}