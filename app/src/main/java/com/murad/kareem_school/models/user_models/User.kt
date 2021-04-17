package com.murad.kareem_school.models.user_models

 open class User {

     private var email:String=""
     private var name:String=""
     private  var dateOfBirth:String=""
     private  var city:String=""
     private var role=""
     private var password=""
     private var profileImage=""
     private var school_name=""
     fun setSchool_name(school:String){

         this.school_name=school
     }
     fun getSchool_name():String{

         return school_name
     }



     fun setProfileImage(uri:String){
         this.profileImage=uri

     }
     fun getProfileImage():String{
         return this.profileImage
     }

     fun setPassword(password:String){
         this.password=password
     }

     fun getPassword()=this.password

      fun setEmail(email:String){
         this.email = email
     }

     fun getEmail():String{
         return this.email
     }

     fun setName(name:String){
         this.name=name
     }
     fun getName():String{
         return name
     }

     fun setDataOfBirth(dateOfBirth:String){

         this.dateOfBirth=dateOfBirth

     }

     fun getDateOfBirth():String{

         return dateOfBirth
     }

     fun setCity(city:String){

         this.city=city
     }

     fun getCity():String{

         return city
     }
     fun setRole(role:String){

         this.role=role
     }
     fun getRole():String{

         return role
     }


 }