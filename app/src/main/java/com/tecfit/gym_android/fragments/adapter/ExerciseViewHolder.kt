package com.tecfit.gym_android.fragments.adapter

import android.content.Context
import android.media.Image
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.fragments.ExerciseFragment
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.custom.RoutinesExercisesInternalStorage
import com.tecfit.gym_android.models.custom.SelectedClasses

class ExerciseViewHolder(val view:View, val manager:FragmentManager?):RecyclerView.ViewHolder(view) {
    val ex_linear= view.findViewById<LinearLayout>(R.id.layout_item_exercise)
    val ex_name = view.findViewById<TextView>(R.id.item_exercise_name)
    val ex_sets = view.findViewById<TextView>(R.id.item_exercise_sets)
    val ex_image = view.findViewById<ImageView>(R.id.item_exercise_image_complete)
    val ex_timeOrRepetitions = view.findViewById<TextView>(R.id.item_exercise_timeOrRepetitions)


    fun render(exercise: Exercise, context: Context?,resInternalStorage: MutableList<RoutinesExercisesInternalStorage>){
        ex_name.text = exercise.name
        ex_sets.text = "Sets: ${exercise.sets}"

        when (exercise.type.id_type) {
            1 -> ex_timeOrRepetitions.text = "Repeticiones: ${exercise.amount}"
            2 -> ex_timeOrRepetitions.text = "Tiempo por serie: ${exercise.amount} seg."
        }
        // Si es true significa que el ejercicio ya está completo
        val exerciseComplete = checkExerciseComplete(resInternalStorage, exercise.id_exercise)

        if (exerciseComplete) {
            ex_image.setBackgroundResource(R.drawable.icon_check_exercise)
            ex_linear.setOnClickListener {
                Toast.makeText(context, "Ya completaste este ejercicio.", Toast.LENGTH_SHORT).show()
            }
        } else {
            val fragmentExercise = ExerciseFragment()
            ex_linear.setOnClickListener{
                SelectedClasses.exercise = exercise
                ForFragments.replaceInFragment(fragmentExercise,manager)
            }
        }


    }

    private fun checkExerciseComplete(resInternalStorage: MutableList<RoutinesExercisesInternalStorage>, id_exercise:Int):Boolean {
        return resInternalStorage.find { re -> re.id_routine == SelectedClasses.routine.id_routine }
            ?.id_exercises?.find { id_exe -> id_exe == id_exercise } != null
    }


}