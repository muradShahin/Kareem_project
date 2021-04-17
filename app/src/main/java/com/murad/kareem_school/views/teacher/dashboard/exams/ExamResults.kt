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
import androidx.recyclerview.widget.LinearLayoutManager
import com.murad.kareem_school.R
import com.murad.kareem_school.helpers.Status
import com.murad.kareem_school.models.exams.Exam
import com.murad.kareem_school.models.exams.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.exams_results.*
import java.lang.Exception

@AndroidEntryPoint
class ExamResults : Fragment() {


    private lateinit var exam:Exam
    private  val TAG = "ExamResults"
    private val viewModel:ExamViewModel by viewModels()
    private val resultsList = ArrayList<Result>()
    private lateinit var resultsAdapter: ResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.exams_results,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        resultsAdapter = ResultsAdapter()
        exams_results.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = resultsAdapter
        }

        try {

            exam = arguments?.getParcelable("exam")!!

            getResults()

        }catch (e:Exception){
            Log.d(TAG, "onViewCreated: ${e.message}")
        }
    }

    fun getResults(){

        viewModel.getExamResults().observe(viewLifecycleOwner, Observer {

            try {

                if(it.status == Status.SUCCESS){

                    val results = it.data?.toObjects(Result::class.java)

                    for (result in results!!){

                        val examF = result.getExam()

                        if(examF.getTitle() == exam.getTitle()){
                            resultsList.add(result)
                        }

                    }

                    resultsAdapter.submitList(resultsList)

                }

            }catch (e:Exception){
                Log.d(TAG, "getResults: ${e.message}")
            }

        })
    }
}