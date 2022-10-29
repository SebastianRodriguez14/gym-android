package com.tecfit.gym_android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.MainActivity
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.databinding.FragmentRoutinesBinding
import com.tecfit.gym_android.databinding.FragmentRoutinesMenuBinding

class RoutinesFragment: Fragment() {

    private lateinit var root: View
    private lateinit var btnFullbody: View
    private lateinit var btnArms: View
    private lateinit var btnLegs: View
    private lateinit var btnChess: View
    private lateinit var btnAbdomen: View
    private lateinit var btnBack: View

    private var routinesListFragment = RoutinesListFragment()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_routines_menu, container, false)

        //activity = MainActivity()
        btnFullbody = root.findViewById(R.id.routine_menu_fullbody)
        btnArms = root.findViewById(R.id.routine_menu_arms)
        btnLegs = root.findViewById(R.id.routine_menu_legs)
        btnChess = root.findViewById(R.id.routine_menu_chess)
        btnAbdomen = root.findViewById(R.id.routine_menu_abdomen)
        btnBack = root.findViewById(R.id.routine_menu_back)

        btnFullbody.setOnClickListener{toRoutine(8)}
        btnArms.setOnClickListener{toRoutine(1)}
        btnLegs.setOnClickListener{toRoutine(2)}
        btnChess.setOnClickListener{toRoutine(5)}
        btnAbdomen.setOnClickListener{toRoutine(4)}
        btnBack.setOnClickListener{toRoutine(3)}



        return root
// pruebalo porfa
    }

    fun toRoutine(id_body_part:Int){
        ForFragments.replaceInFragment(routinesListFragment, fragmentManager)
    }
}


