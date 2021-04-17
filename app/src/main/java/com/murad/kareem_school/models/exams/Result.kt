package com.murad.kareem_school.models.exams

import android.os.Parcel
import android.os.Parcelable
import com.murad.kareem_school.models.user_models.Student

class Result() :Parcelable {

    private var exam = Exam()
    private var student = Student()

    constructor(parcel: Parcel) : this() {
        exam = parcel.readParcelable(Exam::class.java.classLoader)!!
    }

    fun setExam(exam: Exam) {
        this.exam = exam
    }

    fun getExam() = this.exam

    fun setStudent(student: Student) {
        this.student = student
    }

    fun getStudent() = this.student
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(exam, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}