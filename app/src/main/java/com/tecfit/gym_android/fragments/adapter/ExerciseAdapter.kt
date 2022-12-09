package com.tecfit.gym_android.fragments.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.custom.RoutinesExercisesInternalStorage
import org.w3c.dom.Comment

class ExerciseAdapter (private val exerciseList: List<Exercise>,val manager: FragmentManager?,val context: Context?, val bottomSheetDialogComment: BottomSheetDialog, val resInternalStorage: MutableList<RoutinesExercisesInternalStorage>):
    RecyclerView.Adapter<ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(layoutInflater.inflate(R.layout.item_exercise, parent, false),manager, bottomSheetDialogComment)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val item = exerciseList[position]
        holder.render(item, context, resInternalStorage)
    }

    override fun getItemCount(): Int = exerciseList.size

}