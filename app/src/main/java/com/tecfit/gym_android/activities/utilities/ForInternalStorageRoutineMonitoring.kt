package com.tecfit.gym_android.activities.utilities

import android.content.Context
import com.tecfit.gym_android.models.custom.RoutineMonitoring
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*

class ForInternalStorageRoutineMonitoring {

    companion object {

        /*
        * El formato del archivo será el siguiente
        * date_start(lunes de la semana actual)|date_end (domingo de la semana actual)|boolean-boolean-boolean-boolean-boolean-boolean-boolean
        * Los boolean serían false por defecto. */

        private const val FILE_ROUTINE_MONITORING= "routine_monitoring"

        private lateinit var fileOutputStream: FileOutputStream
        private lateinit var fileInputStream: FileInputStream

        fun saveRoutineMonitoring(context: Context?){

            val routineMonitoringInLocalStorage = loadRoutineMonitoring(context)

            val routineMonitoring = checkCurrentWeek(routineMonitoringInLocalStorage)

            val text = formatRoutineMonitoringToText(routineMonitoring)

            try {
                fileOutputStream = context!!.openFileOutput(FILE_ROUTINE_MONITORING, 0)

                fileOutputStream.write(text.toByteArray())

                fileOutputStream.close()
            } catch (e:Exception){
                e.printStackTrace()
            }

        }

        fun completeRoutineInDay(context: Context?){

            val routineMonitoringInLocalStorage = loadRoutineMonitoring(context)

            if (routineMonitoringInLocalStorage != null){

                val calendar: Calendar = Calendar.getInstance()
                calendar.time = Date()
                val day_week = calendar.get(Calendar.DAY_OF_WEEK) - 1

                when(day_week){
                    1 -> routineMonitoringInLocalStorage.doneMonday = true
                    2 -> routineMonitoringInLocalStorage.doneTuesday = true
                    3 -> routineMonitoringInLocalStorage.doneWednesday = true
                    4 -> routineMonitoringInLocalStorage.doneThursday = true
                    5 -> routineMonitoringInLocalStorage.doneFriday = true
                    6 -> routineMonitoringInLocalStorage.doneSaturday = true
                    7 -> routineMonitoringInLocalStorage.doneSunday = true
                }
                val text = formatRoutineMonitoringToText(routineMonitoringInLocalStorage)

                try {
                    fileOutputStream = context!!.openFileOutput(FILE_ROUTINE_MONITORING, 0)

                    fileOutputStream.write(text.toByteArray())

                    fileOutputStream.close()
                } catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }


        // Este método retornará la fecha en la que inicia y termina la semana actual.
        fun checkCurrentWeek(routineMonitoring: RoutineMonitoring?):RoutineMonitoring{
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = Date()
            val day_week = calendar.get(Calendar.DAY_OF_WEEK) - 1
            println("Fecha actual -> ${Date()}")
            println("Día de la semana -> $day_week")

            val date_start_week = Date()
            date_start_week.date = date_start_week.date - (day_week-1)
            println("Fecha inicio de semana -> $date_start_week")

            val date_end_week = Date()
            date_end_week.date = date_end_week.date + (7-day_week)
            println("Fecha final de semana -> $date_end_week")

            val routineMonitoringResult = RoutineMonitoring(date_start_week, date_end_week)

            if (routineMonitoring != null){
                routineMonitoringResult.doneMonday = checkCompleteRoutineInDay(1, day_week, routineMonitoring.doneMonday)
                routineMonitoringResult.doneTuesday = checkCompleteRoutineInDay(2, day_week, routineMonitoring.doneTuesday)
                routineMonitoringResult.doneWednesday = checkCompleteRoutineInDay(3, day_week, routineMonitoring.doneWednesday)
                routineMonitoringResult.doneThursday = checkCompleteRoutineInDay(4, day_week, routineMonitoring.doneThursday)
                routineMonitoringResult.doneFriday = checkCompleteRoutineInDay(5, day_week, routineMonitoring.doneFriday)
                routineMonitoringResult.doneSaturday = checkCompleteRoutineInDay(6, day_week, routineMonitoring.doneSaturday)
                routineMonitoringResult.doneSunday = checkCompleteRoutineInDay(7, day_week, routineMonitoring.doneSunday)
            } else {
                routineMonitoringResult.doneMonday = checkCompleteRoutineInDay(1, day_week, null)
                routineMonitoringResult.doneTuesday = checkCompleteRoutineInDay(2, day_week, null)
                routineMonitoringResult.doneWednesday = checkCompleteRoutineInDay(3, day_week, null)
                routineMonitoringResult.doneThursday = checkCompleteRoutineInDay(4, day_week, null)
                routineMonitoringResult.doneFriday = checkCompleteRoutineInDay(5, day_week, null)
                routineMonitoringResult.doneSaturday = checkCompleteRoutineInDay(6, day_week, null)
                routineMonitoringResult.doneSunday = checkCompleteRoutineInDay(7, day_week, null)
            }



            println(routineMonitoringResult)

            return routineMonitoringResult
        }

        fun checkCompleteRoutineInDay(day_week:Int,day_week_current:Int, complete:Boolean?):Boolean?{

            if (day_week < day_week_current){

                if (complete == null){
                    return false
                } else {
                    return complete
                }

            } else {
                return null
            }

        }

        fun loadRoutineMonitoring(context: Context?):RoutineMonitoring?{
            try{

                fileInputStream = context!!.openFileInput(FILE_ROUTINE_MONITORING)
                val bytes =ByteArray(fileInputStream.available())
                fileInputStream.read(bytes)
                fileInputStream.close()
                val message = String(bytes)
                return formatTextToRoutineMonitoring(message)

            } catch (e:Exception){
                e.printStackTrace()
                return null
            }

        }

        fun formatRoutineMonitoringToText(routineMonitoring: RoutineMonitoring):String =
            "${routineMonitoring.date_start_week}|${routineMonitoring.date_end_week}|" +
                    "${routineMonitoring.doneMonday}-${routineMonitoring.doneTuesday}-${routineMonitoring.doneWednesday}-" +
                    "${routineMonitoring.doneThursday}-${routineMonitoring.doneFriday}-${routineMonitoring.doneSaturday}-" +
                    "${routineMonitoring.doneSunday}"

        fun formatTextToRoutineMonitoring(text:String):RoutineMonitoring?{

            if (text == ""){
                return null
            } else {

                val routine_monitoring_detail = text.split("|")
                val start_date = Date(routine_monitoring_detail.get(0))
                val end_date = Date(routine_monitoring_detail.get(1))
                val days_complete_detail = routine_monitoring_detail.get(2).split("-")

                val routineMonitoring = RoutineMonitoring(start_date, end_date)

                routineMonitoring.doneMonday = if(days_complete_detail.get(0) == "null") null else days_complete_detail.get(0).toBoolean()
                routineMonitoring.doneTuesday = if(days_complete_detail.get(1) == "null") null else days_complete_detail.get(0).toBoolean()
                routineMonitoring.doneWednesday = if(days_complete_detail.get(2) == "null") null else days_complete_detail.get(0).toBoolean()
                routineMonitoring.doneThursday = if(days_complete_detail.get(3) == "null") null else days_complete_detail.get(0).toBoolean()
                routineMonitoring.doneFriday = if(days_complete_detail.get(4) == "null") null else days_complete_detail.get(0).toBoolean()
                routineMonitoring.doneSaturday = if(days_complete_detail.get(5) == "null") null else days_complete_detail.get(0).toBoolean()
                routineMonitoring.doneSunday = if(days_complete_detail.get(6) == "null") null else days_complete_detail.get(0).toBoolean()

                return routineMonitoring

            }


        }

    }

}