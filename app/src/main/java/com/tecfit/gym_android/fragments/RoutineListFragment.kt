package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.fragments.adapter.RoutineAdapter
import com.tecfit.gym_android.models.BodyPart
import com.tecfit.gym_android.models.Routine
import com.tecfit.gym_android.models.custom.ArraysForClass
import com.tecfit.gym_android.models.custom.SelectedClasses
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RoutineListFragment : Fragment() {

    private lateinit var root:View
    private lateinit var btnBackInterface: ImageView
    private lateinit var text_title:TextView
    private lateinit var bodyPart:BodyPart
    private lateinit var recyclerViewRoutine:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_routine_list, container, false)
        btnBackInterface = root.findViewById(R.id.routine_list_body_part_back)
        text_title = root.findViewById(R.id.routine_list_title)
        recyclerViewRoutine = root.findViewById(R.id.recyclerview_routines)
        recyclerViewRoutine.layoutManager = LinearLayoutManager(root.context)

        val routineFragment = RoutineFragment()
        btnBackInterface.setOnClickListener{
            ForFragments.replaceInFragment(routineFragment, fragmentManager)
        }
        bodyPart = BodyPart(SelectedClasses.bodyPart.id_part, SelectedClasses.bodyPart.name, null)
        text_title.text = "Rutinas para ${bodyPart.name}"

        val listRoutinesInLocal = searchListRoutine()
        if (listRoutinesInLocal == null) {
            apiGetRoutinesForBodyPart()
        } else{
            setArrayForRecycler(listRoutinesInLocal as List<Routine>)
        }
//        println("Arreglo de partes del cuerpo ${ArraysForClass.arrayBodyParts}")


        return root

    }

    private fun setArrayForRecycler(routines:List<Routine>) {
        recyclerViewRoutine.adapter = RoutineAdapter(routines, fragmentManager)
    }

    private fun apiGetRoutinesForBodyPart() {

        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultRoutines: Call<List<Routine>> = apiService.getRoutinesForBodyPart(bodyPart.id_part)

        resultRoutines.enqueue(object : Callback<List<Routine>> {
            override fun onResponse(call: Call<List<Routine>>, response: Response<List<Routine>>) {
                val listRoutines = response.body()
//                println(listRoutines)
                if (listRoutines != null) {
                    if (listRoutines.isEmpty()){
                        setArrayForRecycler(mutableListOf())
                    } else{
                        bodyPart.routines = listRoutines
                        ArraysForClass.arrayBodyParts.add(bodyPart)
                        setArrayForRecycler(listRoutines)
                    }

                }
            }
            override fun onFailure(call: Call<List<Routine>>, t: Throwable) {
                println("Error: getRoutinesForBodyPart() failure")
            }
        })

    }

    // Función para verificar si hay rutinas para la parte de un cuerpo ya guardados
    private fun searchListRoutine()
    :Collection<Routine>? {
        println(ArraysForClass.arrayBodyParts)
        return ArraysForClass.arrayBodyParts.find { bp -> bp.id_part == SelectedClasses.bodyPart.id_part }?.routines
    }



}