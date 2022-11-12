package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.databinding.FragmentExerciseBinding
import com.tecfit.gym_android.databinding.FragmentRoutineBinding
import com.tecfit.gym_android.fragments.adapter.RoutineFYAdapter
import com.tecfit.gym_android.models.BodyPart
import com.tecfit.gym_android.models.Routine
import com.tecfit.gym_android.models.custom.ByRandom
import com.tecfit.gym_android.models.custom.SelectedClasses
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoutineFragment : Fragment() {

    lateinit var binding: FragmentRoutineBinding

    private lateinit var root: View
    private lateinit var btnFullbody: View
    private lateinit var btnArms: View
    private lateinit var btnLegs: View
    private lateinit var btnChess: View
    private lateinit var btnAbdomen: View
    private lateinit var btnBack: View

    private var routinesListFragment = RoutineListFragment()

    private lateinit var routineFYList:List<Routine>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRoutineBinding.inflate(layoutInflater)

        ByRandom.byBodyPart = false
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
        apiGetRandomRoutine()
        return binding.root
    }


    fun toRoutine(id_body_part:Int, name:String){
        SelectedClasses.bodyPart = BodyPart(id_body_part, name, null)
        ForFragments.replaceInFragment(routinesListFragment, fragmentManager)
    }

    private fun initRecyclerView(id:Int){
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val recyclerView=root.findViewById<RecyclerView>(id)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter= RoutineFYAdapter(routineFYList, fragmentManager)
    }

    private fun apiGetRandomRoutine() {

        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultRoutines: Call<List<Routine>> = apiService.getRandomRoutines()

        resultRoutines.enqueue(object : Callback<List<Routine>> {
            override fun onResponse(call: Call<List<Routine>>, response: Response<List<Routine>>) {
                val listRoutines = response.body()
                if (listRoutines != null) {
                    routineFYList = listRoutines
                    if(routineFYList.isEmpty()){
                        binding.skeletonForYou.visibility = View.VISIBLE
                    }else{
                        binding.skeletonForYou.visibility = View.INVISIBLE
                        initRecyclerView(R.id.recyclerview_routines_for_you)
                    }
                }
            }
            override fun onFailure(call: Call<List<Routine>>, t: Throwable) {
                println("Error: random routine failed")
            }
        })

    }

}