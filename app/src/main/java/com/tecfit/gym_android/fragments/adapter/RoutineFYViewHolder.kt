package com.tecfit.gym_android.fragments.adapter

import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.fragments.ExerciseListFragment
import com.tecfit.gym_android.models.Routine
import com.tecfit.gym_android.models.custom.ByRandom
import com.tecfit.gym_android.models.custom.SelectedClasses

class RoutineFYViewHolder(val view:View, val manager: FragmentManager?):RecyclerView.ViewHolder(view) {
    val rt_linear = view.findViewById<FrameLayout>(R.id.layout_item_routine_fy)
    val rt_image = view.findViewById<ImageView>(R.id.item_routine_image_fy)
    val rt_name = view.findViewById<TextView>(R.id.item_routine_name_fy)

    fun render(routine: Routine) {
        Glide.with(view.context).load(routine.image.url).into(rt_image)
        rt_name.text = routine.name
        val fragmentExercise = ExerciseListFragment()
        rt_linear.setOnClickListener {
            ByRandom.byBodyPart = false
            SelectedClasses.routine = routine
            ForFragments.replaceInFragment(fragmentExercise, manager)
        }
    }


}