package com.murad.kareem_school.models.exams

import android.os.Parcel
import android.os.Parcelable

class Exam() : Parcelable{

    private var title: String = ""
    private var active: Boolean = false
    private var teacherEmail: String = ""
    private var grade : String=""
    private var listOfAnswers: ArrayList<Question> = ArrayList()

    constructor(parcel: Parcel) : this() {
        title = parcel.readString().toString()
        active = parcel.readByte() != 0.toByte()
        teacherEmail = parcel.readString().toString()
        grade = parcel.readString().toString()
    }


    fun setTitle(title: String) {
        this.title = title
    }

    fun getTitle() = this.title

    fun setListOfAnswers(list: ArrayList<Question>) {
        this.listOfAnswers = list
    }

    fun getListOfAnswers() = this.listOfAnswers

    fun setTeacherEmail(email: String) {
        this.teacherEmail = email
    }

    fun getTeacherEmail() = this.teacherEmail

    fun setActive(active: Boolean) {
        this.active = active
    }

    fun getActive() = this.active

    fun setGrade(grade:String){
        this.grade = grade
    }

    fun getGrade() = this.grade
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeString(teacherEmail)
        parcel.writeString(grade)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exam> {
        override fun createFromParcel(parcel: Parcel): Exam {
            return Exam(parcel)
        }

        override fun newArray(size: Int): Array<Exam?> {
            return arrayOfNulls(size)
        }
    }
}