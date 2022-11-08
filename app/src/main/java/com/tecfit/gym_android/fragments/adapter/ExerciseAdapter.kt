package com.tecfit.gym_android.fragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.custom.RoutinesExercisesInternalStorage

class ExerciseAdapter (private val exerciseList: List<Exercise>, val manager: FragmentManager?,val context: Context?, val resInternalStorage: MutableList<RoutinesExercisesInternalStorage>):

    RecyclerView.Adapter<ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(layoutInflater.inflate(R.layout.item_exercise, parent, false), manager)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = exerciseList[position]
        holder.render(item, context, resInternalStorage)
    }

    override fun getItemCount(): Int = exerciseList.size

}