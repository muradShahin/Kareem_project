package com.murad.kareem_school.views.teacher.dashboard.exams

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.R
import com.murad.kareem_school.models.exams.Result
import kotlinx.android.synthetic.main.resuls_row.view.*
import java.lang.Exception


class ResultsAdapter : ListAdapter<Result,ResultsAdapter.ResultsViewHolder>(ResultsDiffUtil()) {

    private  val TAG = "ResultsAdapter"

    inner class ResultsViewHolder(val layout:ConstraintLayout) :RecyclerView.ViewHolder(layout){


        fun binds(result: Result){

            try {

                itemView.student_name.text = result.getStudent().getName()

                itemView.q1.text = "${result.getExam().getListOfAnswers()[0].getQuestion()} answer : ${result.getExam().getListOfAnswers()[0].getAnswer()}"
                itemView.q2.text = "${result.getExam().getListOfAnswers()[1].getQuestion()} answer : ${result.getExam().getListOfAnswers()[1].getAnswer()}"
                itemView.q3.text = "${result.getExam().getListOfAnswers()[2].getQuestion()} answer : ${result.getExam().getListOfAnswers()[2].getAnswer()}"
                itemView.q4.text = "${result.getExam().getListOfAnswers()[3].getQuestion()} answer : ${result.getExam().getListOfAnswers()[3].getAnswer()}"
                itemView.q5.text = "${result.getExam().getListOfAnswers()[4].getQuestion()} answer : ${result.getExam().getListOfAnswers()[4].getAnswer()}"


            }catch (e:Exception){
                Log.d(TAG, "binds: ${e.message}")
            }
        }
    }

    class ResultsDiffUtil:DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result) = oldItem.getExam().getTitle() == newItem.getExam().getTitle()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Result, newItem: Result) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
            .inflate(R.layout.resuls_row,parent,false) as ConstraintLayout

        return ResultsViewHolder(layoutInflater)
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {

        holder.binds(getItem(position))
    }
}