package com.tecfit.gym_android.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Trainer

class TrainerAdapter(private val trainerList:List<Trainer>) : RecyclerView.Adapter<TrainerViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int):TrainerViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        return TrainerViewHolder(layoutInflater.inflate(R.layout.item_entrenador,parent,false))
    }

    override fun onBindViewHolder(holder:TrainerViewHolder, position:Int){
        val item = trainerList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = trainerList.size

}