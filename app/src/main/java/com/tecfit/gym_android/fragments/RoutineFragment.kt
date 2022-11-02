package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.models.BodyPart
import com.tecfit.gym_android.models.custom.SelectedClasses

class RoutineFragment : Fragment() {

    private lateinit var root: View
    private lateinit var btnFullbody: View
    private lateinit var btnArms: View
    private lateinit var btnLegs: View
    private lateinit var btnChess: View
    private lateinit var btnAbdomen: View
    private lateinit var btnBack: View

    private var routinesListFragment = RoutineListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root =  inflater.inflate(R.layout.fragment_routine, container, false)
        btnFullbody = root.findViewById(R.id.routine_menu_fullbody)
        btnArms = root.findViewById(R.id.routine_menu_arms)
        btnLegs = root.findViewById(R.id.routine_menu_legs)
        btnChess = root.findViewById(R.id.routine_menu_chess)
        btnAbdomen = root.findViewById(R.id.routine_menu_abdomen)
        btnBack = root.findViewById(R.id.routine_menu_back)

        btnFullbody.setOnClickListener{toRoutine(8, "Cuerpo Completo")}
        btnArms.setOnClickListener{toRoutine(1, "Brazos")}
        btnLegs.setOnClickListener{toRoutine(2, "Piernas")}
        btnChess.setOnClickListener{toRoutine(5, "Pecho")}
        btnAbdomen.setOnClickListener{toRoutine(4, "Abdomen")}
        btnBack.setOnClickListener{toRoutine(3, "Espalda")}

        return root
    }


    fun toRoutine(id_body_part:Int, name:String){
        SelectedClasses.bodyPart = BodyPart(id_body_part, name, null)
        ForFragments.replaceInFragment(routinesListFragment, fragmentManager)
    }

}