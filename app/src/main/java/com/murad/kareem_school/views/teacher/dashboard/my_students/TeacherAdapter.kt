package com.murad.kareem_school.views.teacher.dashboard.my_students

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.murad.kareem_school.R
import com.murad.kareem_school.databinding.UserRowBinding
import com.murad.kareem_school.models.user_models.Student
import com.murad.kareem_school.models.user_models.User

class TeacherAdapter(val fragment:TeacherStudents,val onAddClick:OnAddClicked) : ListAdapter<Student,TeacherAdapter.TeacherViewHolder>(UsersDiffUtil()) {

    interface OnAddClicked{
        fun onClick(student: Student)
    }

   inner class TeacherViewHolder(val view:UserRowBinding) : RecyclerView.ViewHolder(view.root){

        fun binds(student: Student){
            view.user = student
            view.addBtn.setOnClickListener{
                it.isEnabled=false
                onAddClick.onClick(student)
            }
        }
    }


    class UsersDiffUtil : DiffUtil.ItemCallback<Student>(){
        override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem.getEmail() == newItem.getEmail()

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Student, newItem: Student) = oldItem == newItem


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val userBinding = UserRowBinding.inflate(layoutInflater,parent,false)

        return TeacherViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {

        holder.binds(getItem(position))

    }
}