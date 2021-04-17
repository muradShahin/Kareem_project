package com.murad.kareem_school.helpers


enum class RoleStatus{

    STUDENT,
    Teacher
}

class Role(val role:RoleStatus) {

    companion object{
        var STUDENT : Role
        var Teacher : Role

        init {
            STUDENT = Role(RoleStatus.STUDENT)
            Teacher = Role(RoleStatus.Teacher)

        }
    }

}