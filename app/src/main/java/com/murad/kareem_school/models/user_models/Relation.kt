package com.murad.kareem_school.models.user_models

 class Relation {

     private var teacher = Teacher()
     private var student = Student()

     fun setTeacher(teacher: Teacher) {
         this.teacher=teacher
     }

     fun setStudent(student: Student){
         this.student=student
     }
     fun getStudent():Student{
         return student
     }
     fun getTeacher():Teacher{
         return teacher
     }


 }