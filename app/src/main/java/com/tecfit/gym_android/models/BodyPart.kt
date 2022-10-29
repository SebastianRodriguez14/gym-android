package com.tecfit.gym_android.models

data class BodyPart(var id_part:Int, var name:String, var routines: Collection<Routine>?) {
}