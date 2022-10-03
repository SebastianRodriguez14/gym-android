package com.tecfit.gym_android.fragments.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Trainer

class TrainerViewHolder(val view: View):RecyclerView.ViewHolder(view) {
    val tr_image = view.findViewById<ImageView>(R.id.item_trainer_image)
    val tr_name = view.findViewById<TextView>(R.id.text_name_trainner)
    val tr_description = view.findViewById<TextView>(R.id.text_description_trainner)

    fun render(trainer: Trainer){
        Glide.with(view.context).load(trainer.file).into(tr_image)
        tr_name.text=trainer.name
        tr_name.text=trainer.lastname
        tr_description.text=trainer.description

    }
}