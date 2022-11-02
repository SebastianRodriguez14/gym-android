package com.tecfit.gym_android.models

data class Routine (val id_routine:Int, val name:String, val image:File, var exercise:Collection<Exercise>?)  {
}