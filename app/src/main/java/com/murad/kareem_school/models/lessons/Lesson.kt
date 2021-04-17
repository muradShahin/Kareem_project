package com.murad.kareem_school.models.lessons

import android.os.Parcel
import android.os.Parcelable
import com.murad.kareem_school.models.user_models.Teacher


class Lesson() : Parcelable {

    private var title_lesson: String? = null
    private var grade: String? = null
    private var subject: String? = null
    private var imageUri: String? = null
    private var videoUri: String? = null
    private var teacher: Teacher? = null
    private var date: String? = null
    private var desc: String? = null

    constructor(parcel: Parcel) : this() {
        title_lesson = parcel.readString()
        grade = parcel.readString()
        subject = parcel.readString()
        imageUri = parcel.readString()
        videoUri = parcel.readString()
        date = parcel.readString()
        desc = parcel.readString()
    }


    fun setTitle_lesson(title_lesson: String) {
        this.title_lesson = title_lesson
    }

    fun getTitle_lesson() = this.title_lesson

    fun setGrade(grade: String) {
        this.grade = grade
    }

    fun getGrade() = this.grade

    fun setSubject(subject: String) {
        this.subject = subject
    }

    fun getSubject() = this.subject

    fun setImageUri(imageUri: String) {
        this.imageUri = imageUri
    }

    fun getImageUri() = this.imageUri

    fun setVideoUri(videoUri: String) {
        this.videoUri=videoUri
    }

    fun getVideoUri() = this.videoUri

    fun setTeacher(teacher: Teacher) {
        this.teacher = teacher
    }

    fun getTeacher() = this.teacher

    fun setDate(date: String) {
        this.date = date
    }

    fun getDate() = this.date

    fun setDesc(desc: String) {
        this.desc = desc
    }

    fun getDesc() = this.desc
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title_lesson)
        parcel.writeString(grade)
        parcel.writeString(subject)
        parcel.writeString(imageUri)
        parcel.writeString(videoUri)
        parcel.writeString(date)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }


}