package com.tecfit.gym_android.activities.utilities

import android.content.Context
import android.widget.Toast
import com.tecfit.gym_android.fragments.ExerciseFragment
import com.tecfit.gym_android.models.custom.RoutinesExercisesInternalStorage
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.util.concurrent.RecursiveTask

class ForInternalStorage {
    /*
    * Formato en el que guardaremos los datos
    * id_rutina:id_ejercicio01-id_ejercicio02...id_ejercicioN
    * Separaremos cada bloque de datos con -> '|'
    * Usaremos la clase custom para recibir esta información.
    * Solo se guardarán rutinas de las cuales se hayan empezado aunque sea un ejercicio.
    * Es decir, en el cumplimiento de un ejercicio, se tendrá que validar si ya está esa rutina guardada localmente
    * Si no lo está significa que es el primer ejercicio que se completa de esa rutina, entonces se registra tanto rutina como ejercicio.
    * Si está significa que ya se ha completado un ejercicio de esa rutina anteriormente, así que solo se adiciona el ejercicio al arreglo de
    * id's de ejercicios.
    */

    companion object {

        private const val FILE_ROUTINE_EXERCISES = "routines_exercises"
        private const val FILE_COUNTERS = "routines_counter"

        private lateinit var fileOutputStream:FileOutputStream
        private lateinit var fileInputStream: FileInputStream

        fun saveRoutinesAndExercises(routinesExercisesInternalStorage: MutableList<RoutinesExercisesInternalStorage>, context:Context?){

            val text = formatRoutinesExercisesToText(routinesExercisesInternalStorage)

            try {
                // 0 -> MODE_PRIVATE
                fileOutputStream = context!!.openFileOutput(FILE_ROUTINE_EXERCISES, 0)

                fileOutputStream.write(text.toByteArray())

                Toast.makeText(context, "Saved text to ${context.filesDir}", Toast.LENGTH_SHORT).show()

            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun loadRoutinesAndExercises(context: Context?):MutableList<RoutinesExercisesInternalStorage> {
            var resInternalStorage = mutableListOf<RoutinesExercisesInternalStorage>()
            try {
                fileInputStream = context!!.openFileInput(FILE_ROUTINE_EXERCISES)
                val bytes =ByteArray(fileInputStream.available())
                fileInputStream.read(bytes)
                fileInputStream.close()
                val message = String(bytes)

                resInternalStorage = formatTextToRoutinesAndExercises(message)

            } catch (e:Exception) {
                e.printStackTrace()
            }
            return resInternalStorage
        }

        private fun formatRoutinesExercisesToText(routinesExercisesInternalStorage: MutableList<RoutinesExercisesInternalStorage>):String{
            var text = ""

            if (routinesExercisesInternalStorage.isEmpty()){
                return text
            } else {

                for (reInternalStorage in routinesExercisesInternalStorage){
                    // sub_text -> "1:" (esto es un ejemplo)
                    var sub_text = "${reInternalStorage.id_routine}:"

                    for (id_exercises in reInternalStorage.id_exercises){
                        sub_text += "${id_exercises}-"
                    }
                    // sub_text -> "1:2-3-4" (esto es un ejemplo de como se vería el sub_text después de la siguiente línea)
                    sub_text = sub_text.substring(0, sub_text.length-1)

                    // text -> "1:2-3-4|" (un ejemplo de como se vería text)
                    text += "$sub_text|"

                }
                // text -> "1:2-3-4|2:1-2-4|3:1-2-1" (un ejemplo de como se vería text después de la siguiente línea)
                text = text.substring(0, text.length-1)
                return text
            }
        }

        private fun formatTextToRoutinesAndExercises(text:String):MutableList<RoutinesExercisesInternalStorage>{
            val resInternalStorage = mutableListOf<RoutinesExercisesInternalStorage>()

            if (text.isEmpty()) {
                return mutableListOf()
            }

            // separate_routines -> [ "1:1-2-3", "2:2-3-4"] (ejemplo de como debería verse)
            val separate_routines = text.split("|")

            for (routine in separate_routines){
                // routine_detail -> [ "1", "1-2-3"] (ejemplo)
                val routine_detail = routine.split(":")
                val id_routine = routine_detail.get(0).toInt()
                val id_exercises = mutableListOf<Int>()

                //id_exercises -> [ "1", "2", "3" ] (ejemplo)
                val id_exercises_text = routine_detail.get(1).split("-")

                for (id_exercise in id_exercises_text){
                    id_exercises.add(id_exercise.toInt())
                }

                val reInternalStorage = RoutinesExercisesInternalStorage(id_routine, id_exercises)

                resInternalStorage.add(reInternalStorage)

            }

            return resInternalStorage

        }

    }



}