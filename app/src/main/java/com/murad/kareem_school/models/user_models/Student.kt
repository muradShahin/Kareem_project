package com.murad.kareem_school.models.user_models

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.murad.kareem_school.R

class Student: User() {

     private var grade : String =""


     fun setGrade(grade:String){
         this.grade=grade
     }

     fun getGrade():String{
         return this.grade
     }





    companion object {

        @JvmStatic
        @BindingAdapter("android:loadImage")
        fun loadImage(imageView: ImageView, imageUri: String) {
            Glide.with(imageView)
                .load(imageUri)
                .placeholder(R.drawable.user)
                .into(imageView)
        }

    }


 }