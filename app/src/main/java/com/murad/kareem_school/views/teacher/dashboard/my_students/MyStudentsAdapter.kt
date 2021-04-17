package com.murad.kareem_school.views.teacher.dashboard.my_students

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.databinding.UserRowBinding
import com.murad.kareem_school.models.user_models.Student


class MyStudentsAdapter(fragment: TeacherStudents) :
    ListAdapter<Student, MyStudentsAdapter.MyStudentsViewHolder>(StudentsDiffUtil()) {


    inner class MyStudentsViewHolder(val userRowBinding: UserRowBinding) :
        RecyclerView.ViewHolder(userRowBinding.root) {

        fun binds(student: Student) {

            try {

                userRowBinding.user = student
                userRowBinding.addBtn.visibility = View.GONE

            } catch (e: Exception) {

            }

        }
    }

    class StudentsDiffUtil : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Student, newItem: Student) =
            oldItem.getEmail() == newItem.getEmail()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyStudentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val userRowBinding = UserRowBinding.inflate(layoutInflater, parent, false)
        return MyStudentsViewHolder(userRowBinding)
    }

    override fun onBindViewHolder(holder: MyStudentsViewHolder, position: Int) {
        holder.binds(getItem(position))
    }
}