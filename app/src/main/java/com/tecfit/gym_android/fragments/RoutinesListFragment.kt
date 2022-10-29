package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments

class RoutinesListFragment:Fragment() {

    private lateinit var root:View
    private val routinesFragment = RoutinesFragment()
    private lateinit var btnBackInterface: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_routines, container, false)
        btnBackInterface = root.findViewById(R.id.routine_body_part_back)
        btnBackInterface.setOnClickListener {
            ForFragments.replaceInFragment(routinesFragment, fragmentManager)
        }

        return root
    }
}