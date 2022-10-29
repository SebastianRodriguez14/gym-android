package com.tecfit.gym_android.fragments.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Routine

class RoutineViewHolder(val view:View):RecyclerView.ViewHolder(view) {
    val rt_image = view.findViewById<ImageView>(R.id.item_routine_image)
    val rt_name = view.findViewById<TextView>(R.id.item_routine_name)

    fun render(routine: Routine) {
        Glide.with(view.context).load(routine.image.url).into(rt_image)
        rt_name.text = routine.name
    }


}