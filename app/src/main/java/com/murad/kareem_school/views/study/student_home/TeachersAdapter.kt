package com.murad.kareem_school.views.study.student_home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.murad.kareem_school.views.teacher.dashboard.my_students.TeacherAdapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.murad.kareem_school.R
import com.murad.kareem_school.models.user_models.Teacher
import kotlinx.android.synthetic.main.teacher_row.view.*

class TeachersAdapter(val fragment: Fragment) :
    ListAdapter<Teacher, TeachersAdapter.TeachersViewHolder>(TeachersDiffUtil()) {


    inner class TeachersViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun binds(teacher: Teacher) {

            try {

                itemView.teacher_name.text = teacher.getName()

                Glide.with(fragment)
                    .load(teacher.getProfileImage())
                    .placeholder(R.drawable.user)
                    .into(itemView.imageView6)

            } catch (e: Exception) {

            }


        }
    }


    class TeachersDiffUtil : DiffUtil.ItemCallback<Teacher>() {
        override fun areItemsTheSame(oldItem: Teacher, newItem: Teacher) = oldItem == newItem

        override fun areContentsTheSame(oldItem: Teacher, newItem: Teacher) =
            oldItem.getEmail() == newItem.getEmail()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeachersViewHolder {
        return TeachersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.teacher_row, parent, false) as View
        )

    }

    override fun onBindViewHolder(holder: TeachersViewHolder, position: Int) {

        holder.binds(getItem(position))

    }


}