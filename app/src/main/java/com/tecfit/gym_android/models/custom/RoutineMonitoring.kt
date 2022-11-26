package com.tecfit.gym_android.models.custom

import java.util.*

data class RoutineMonitoring (val date_start_week:Date, val date_end_week:Date,
                              var doneMonday:Boolean? = null, // Lunes
                              var doneTuesday:Boolean? = null, // Martes
                              var doneWednesday:Boolean? = null, // Miércoles
                              var doneThursday:Boolean? = null, // Jueves
                              var doneFriday:Boolean? = null, // Viernes
                              var doneSaturday:Boolean? = null, // Sábado
                              var doneSunday:Boolean? = null // Domingo
)