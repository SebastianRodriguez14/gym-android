package com.tecfit.gym_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.activities.utilities.ForInternalStorageRoutines
import com.tecfit.gym_android.fragments.adapter.ExerciseAdapter
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.custom.ArraysForClass
import com.tecfit.gym_android.models.custom.RoutinesExercisesInternalStorage
import com.tecfit.gym_android.models.custom.ByRandom
import com.tecfit.gym_android.models.custom.SelectedClasses
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExerciseListFragment : Fragment() {

    private lateinit var imageRoutine:ImageView
    private lateinit var imageBackToRoutine:ImageView
    private lateinit var buttonRestartExercises:LinearLayout
    private lateinit var buttonCompleteExercise:LinearLayout
    private lateinit var recyclerViewExercise:RecyclerView
    private lateinit var root:View
    private var tempExercises= listOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_exercise_list, container, false)

        buttonRestartExercises = root.findViewById(R.id.exercise_list_restart)
        buttonCompleteExercise = root.findViewById(R.id.exercise_list_button_complete)
        imageRoutine = root.findViewById(R.id.exercise_list_image_routine)
        imageBackToRoutine = root.findViewById(R.id.exercise_list_body_part_back)
        val routineListFragment = RoutineListFragment()
        recyclerViewExercise = root.findViewById(R.id.recyclerview_exercises)
        recyclerViewExercise.layoutManager = LinearLayoutManager(root.context)


        Glide.with(root.context).load(SelectedClasses.routine.image.url).into(imageRoutine)
        imageBackToRoutine.setOnClickListener {
            println("----ACA ES----")
            println(ByRandom.byBodyPart)
            if(ByRandom.byBodyPart){
                ForFragments.replaceInFragment(routineListFragment, fragmentManager)
            }else{
                val fragmentroutinelist = RoutineFragment()
                val transaction = fragmentManager?.beginTransaction()
                transaction?.replace(R.id.frame_container, fragmentroutinelist)?.commit()
            }
        }

        buttonRestartExercises.setOnClickListener {
            deleteRoutine()
        }
        buttonCompleteExercise.setOnClickListener {
            deleteRoutine()
            buttonCompleteExercise.isVisible = false
        }


        val exercises:List<Exercise>? = searchExercisesForRoutine()

        if (exercises == null) {
//            println("Saco info de la bd")
            apiGetExerciseByRoutine()
        } else{
//            println("Saco info de los arreglos locales")
            tempExercises = exercises
            checkCompleteExercises(exercises)
            setArrayForRecycler(exercises)
        }
        return root
    }

    private fun deleteRoutine(){
        val resInternalStorage = ForInternalStorageRoutines.loadRoutinesAndExercises(context)

        for(i in 0 until resInternalStorage.size){
            if (resInternalStorage.get(i).id_routine == SelectedClasses.routine.id_routine){
                resInternalStorage.removeAt(i)
                break
            }
        }
        ForInternalStorageRoutines.saveRoutinesAndExercises(resInternalStorage, context)
        setArrayForRecycler(tempExercises)
    }

    private fun checkCompleteExercises(exercises: List<Exercise>){
        val reInternalStorage =  ForInternalStorageRoutines.loadRoutinesAndExercises(context).find{ re -> re.id_routine == SelectedClasses.routine.id_routine}
        // true -> Completado
        // false -> faltaron algunos
//        println("Internal storage -> $reInternalStorage")
//        println("Ejercicios -> ${exercises}")
        var complete = true
        for (exercise in exercises){
            val tempExercise = reInternalStorage?.id_exercises?.find { id -> id == exercise.id_exercise }
            if (tempExercise == null) {
                complete = false
                break
            }
        }

        buttonRestartExercises.isVisible = !complete
        buttonCompleteExercise.isVisible = complete



    }




    private fun setArrayForRecycler(exercises:List<Exercise>) {
        val resInternalStorage: MutableList<RoutinesExercisesInternalStorage> = ForInternalStorageRoutines.loadRoutinesAndExercises(context)
        recyclerViewExercise.adapter = ExerciseAdapter(exercises, fragmentManager, context,resInternalStorage)
    }

    private fun updateExercisesForRoutine(exercises: List<Exercise>){
        ArraysForClass.arrayBodyParts.find { bd -> SelectedClasses.bodyPart.id_part == bd.id_part
        }?.routines?.find { r -> r.id_routine == SelectedClasses.routine.id_routine }?.exercise = exercises
    }

    private fun searchExercisesForRoutine(): List<Exercise>? {
        val bodyPart = ArraysForClass.arrayBodyParts.find { bd -> SelectedClasses.bodyPart.id_part == bd.id_part }
//        println("Parte del cuerpo seleccionada")
//        println(bodyPart)
        val routines = bodyPart?.routines?.find { r -> r.id_routine == SelectedClasses.routine.id_routine }
//        println("Rutina seleccionada")
//        println(SelectedClasses.routine)
//        println("Rutina extraída")
//        println(routines)
//        tempExercises = routines?.exercise?.toList()!!
//        println("Ejercicios -> $tempExercises")
        return routines?.exercise?.toList()
    }


    private fun apiGetExerciseByRoutine() {

        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultExercise: Call<List<Exercise>> = apiService.getExercisesByRoutine(SelectedClasses.routine.id_routine)

        resultExercise.enqueue(object : Callback<List<Exercise>> {
            override fun onResponse(call: Call<List<Exercise>>, response: Response<List<Exercise>>) {
                val listExercise = response.body()
//                println(listExercise)
                if (listExercise != null) {
                    tempExercises = listExercise
                    setArrayForRecycler(listExercise)
                    checkCompleteExercises(listExercise)
                    updateExercisesForRoutine(listExercise)
                }
            }
            override fun onFailure(call: Call<List<Exercise>>, t: Throwable) {
                println("Error: apiGetExerciseByRoutine() failure")
            }
        })
    }

}