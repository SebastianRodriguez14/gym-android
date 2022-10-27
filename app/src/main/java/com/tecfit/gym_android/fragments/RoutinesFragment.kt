package com.tecfit.gym_android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.MainActivity
import com.tecfit.gym_android.activities.utilities.ForFragments

class RoutinesFragment: Fragment() {

    private lateinit var root: View
    private lateinit var btnCuerpo: View
    private lateinit var activity: MainActivity
    private val routinesListFragment = RoutinesListFragment()


    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_routines_menu, container, false)
        //activity = MainActivity()
        btnCuerpo = root.findViewById(R.id.btn_cuerpo)

        btnCuerpo.setOnClickListener{
            val fragmentroutinelist = RoutinesListFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame_container, fragmentroutinelist)?.commit()
        }
        return root
// pruebalo porfa
    }
}


