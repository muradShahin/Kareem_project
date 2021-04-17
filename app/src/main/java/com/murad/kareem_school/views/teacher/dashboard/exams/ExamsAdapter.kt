package com.murad.kareem_school.views.teacher.dashboard.exams

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.R
import com.murad.kareem_school.databinding.ExamTeacherRowBinding
import com.murad.kareem_school.models.exams.Exam


class ExamsAdapter(val fragment: ExamsTeacher) :
    ListAdapter<Exam, ExamsAdapter.ExamViewHolder>(ExamsDiffUtil()) {


    inner class ExamViewHolder(val view: ExamTeacherRowBinding) :
        RecyclerView.ViewHolder(view.root) {

        fun binds(exam: Exam) {

            try {
                view.exam = exam

                view.seeResults.setOnClickListener {

                    fragment.seeResult(exam)
                }

            } catch (e: Exception) {

            }

        }
    }

    class ExamsDiffUtil : DiffUtil.ItemCallback<Exam>() {
        override fun areItemsTheSame(oldItem: Exam, newItem: Exam) =
            oldItem.getTitle() == newItem.getTitle() && oldItem.getTeacherEmail() == newItem.getTeacherEmail()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Exam, newItem: Exam) = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val examRowBinding = ExamTeacherRowBinding.inflate(layoutInflater, parent, false)
        return ExamViewHolder(examRowBinding)
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        holder.binds(getItem(position))
    }
}