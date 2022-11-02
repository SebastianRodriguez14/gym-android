package com.tecfit.gym_android.fragments.adapter

import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Exercise

class ExerciseViewHolder(val view:View, val manager:FragmentManager?):RecyclerView.ViewHolder(view) {

    val ex_name = view.findViewById<TextView>(R.id.item_exercise_name)
    val ex_sets = view.findViewById<TextView>(R.id.item_exercise_sets)
    val ex_timeOrRepetitions = view.findViewById<TextView>(R.id.item_exercise_timeOrRepetitions)

    fun render(exercise: Exercise){
        ex_name.text = exercise.name
        ex_sets.text = "Sets: ${exercise.sets}"

        when (exercise.type.id_type) {
            1 -> ex_timeOrRepetitions.text = "Repeticiones: ${exercise.amount}"
            2 -> ex_timeOrRepetitions.text = "Tiempo por serie: ${exercise.amount} seg."
        }

    }
}