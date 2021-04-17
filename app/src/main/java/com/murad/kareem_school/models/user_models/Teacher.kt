package com.murad.kareem_school.models.user_models

class Teacher : User() {

    private var subject:String=""

    fun setSubject(subject: String){

        this.subject=subject
    }

    fun getSubject():String{

        return this.subject
    }
}