package com.tecfit.gym_android.models

data class User(
    val id_user:Int, val email:String, val password:String, val name:String, val lastname:String,
    val phone:String, var membership:Boolean, var image: File?
) {
}