package com.murad.kareem_school.views.study.exams

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.databinding.ExamTeacherRowBinding
import com.murad.kareem_school.databinding.StudentExamRowBinding
import com.murad.kareem_school.models.exams.Exam

class ExamsAdapter(val fragment: ExamFragment) : ListAdapter<Exam, ExamsAdapter.ExamsViewHolder>(ExamsDiffUtil()) {


    inner class ExamsViewHolder(val view: StudentExamRowBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun binds(exam: Exam) {

            view.exam = exam

            view.seeResults.setOnClickListener {

                fragment.startExam(exam)
            }
        }
    }

    class ExamsDiffUtil : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam) =
            oldItem.getTitle() == newItem.getTitle() && oldItem.getTeacherEmail() == newItem.getTeacherEmail()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Exam, newItem: Exam) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamsViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val studentExamRowBinding = StudentExamRowBinding.inflate(layoutInflater, parent, false)

        return ExamsViewHolder(studentExamRowBinding)
    }

    override fun onBindViewHolder(holder: ExamsViewHolder, position: Int) {

        holder.binds(getItem(position))
    }


}