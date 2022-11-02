package com.tecfit.gym_android.fragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.models.Routine

class RoutineAdapter (private val routineList:List<Routine>, val manager:FragmentManager?) : RecyclerView.Adapter<RoutineViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return RoutineViewHolder(layoutInflater.inflate(R.layout.item_routine,parent,false), manager)


    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val item = routineList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = routineList.size


}