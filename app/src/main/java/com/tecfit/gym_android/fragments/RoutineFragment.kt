package com.tecfit.gym_android.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.activities.utilities.ForInternalStorageRoutineMonitoring
import com.tecfit.gym_android.fragments.adapter.RoutineFYAdapter
import com.tecfit.gym_android.models.BodyPart
import com.tecfit.gym_android.models.Routine
import com.tecfit.gym_android.models.custom.ByRandom
import com.tecfit.gym_android.models.custom.SelectedClasses
import com.tecfit.gym_android.models.custom.UserInAppCustom
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoutineFragment : Fragment() {



    private lateinit var root: View
    private lateinit var btnFullbody: View
    private lateinit var btnArms: View
    private lateinit var btnLegs: View
    private lateinit var btnChess: View
    private lateinit var btnAbdomen: View
    private lateinit var btnBack: View
    private lateinit var routineUsername:TextView
    private lateinit var routineUserPhoto:ImageView

    private lateinit var skeleton:CardView

    private var routinesListFragment = RoutineListFragment()

    private lateinit var routineFYList:List<Routine>

    private var dayCurrentWeek:Int = -1

    private lateinit var recyclerViewRandomRoutines:RecyclerView


    private lateinit var layoutMonday:LinearLayout
    private lateinit var layoutTuesday:LinearLayout
    private lateinit var layoutWednesday:LinearLayout
    private lateinit var layoutThursday:LinearLayout
    private lateinit var layoutFriday:LinearLayout
    private lateinit var layoutSunday:LinearLayout
    private lateinit var layoutSaturday:LinearLayout

    private lateinit var iconMonday:ImageView
    private lateinit var iconTuesday:ImageView
    private lateinit var iconWednesday:ImageView
    private lateinit var iconThursday:ImageView
    private lateinit var iconFriday:ImageView
    private lateinit var iconSunday:ImageView
    private lateinit var iconSaturday:ImageView

    private lateinit var textMonday:TextView
    private lateinit var textTuesday:TextView
    private lateinit var textWednesday:TextView
    private lateinit var textThursday:TextView
    private lateinit var textFriday:TextView
    private lateinit var textSunday:TextView
    private lateinit var textSaturday:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root =  inflater.inflate(R.layout.fragment_routine, container, false)
        ByRandom.byBodyPart = false

        dayCurrentWeek = ForInternalStorageRoutineMonitoring.getCurrentDay()

        layoutMonday = root.findViewById(R.id.routine_monday_layout)
        layoutTuesday = root.findViewById(R.id.routine_tuesday_layout)
        layoutWednesday = root.findViewById(R.id.routine_wednesday_layout)
        layoutThursday = root.findViewById(R.id.routine_thursday_layout)
        layoutFriday = root.findViewById(R.id.routine_friday_layout)
        layoutSunday = root.findViewById(R.id.routine_sunday_layout)
        layoutSaturday = root.findViewById(R.id.routine_saturday_layout)


        iconMonday = root.findViewById(R.id.routine_monday_icon)
        iconTuesday = root.findViewById(R.id.routine_tuesday_icon)
        iconWednesday = root.findViewById(R.id.routine_wednesday_icon)
        iconThursday = root.findViewById(R.id.routine_thursday_icon)
        iconFriday = root.findViewById(R.id.routine_friday_icon)
        iconSunday = root.findViewById(R.id.routine_sunday_icon)
        iconSaturday = root.findViewById(R.id.routine_saturday_icon)

        textMonday = root.findViewById(R.id.routine_monday_text)
        textTuesday = root.findViewById(R.id.routine_tuesday_text)
        textWednesday = root.findViewById(R.id.routine_wednesday_text)
        textThursday = root.findViewById(R.id.routine_thursday_text)
        textFriday = root.findViewById(R.id.routine_friday_text)
        textSunday = root.findViewById(R.id.routine_sunday_text)
        textSaturday = root.findViewById(R.id.routine_saturday_text)


        verifyRoutinesAllDays()

        btnFullbody = root.findViewById(R.id.routine_menu_fullbody)
        btnArms = root.findViewById(R.id.routine_menu_arms)
        btnLegs = root.findViewById(R.id.routine_menu_legs)
        btnChess = root.findViewById(R.id.routine_menu_chess)
        btnAbdomen = root.findViewById(R.id.routine_menu_abdomen)
        btnBack = root.findViewById(R.id.routine_menu_back)
        skeleton = root.findViewById(R.id.skeleton_for_you)
        routineUsername = root.findViewById(R.id.routine_user_name)
        routineUserPhoto = root.findViewById(R.id.routine_user_photo)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewRandomRoutines=root.findViewById(R.id.recyclerview_routines_for_you)
        recyclerViewRandomRoutines.layoutManager = layoutManager
        checkUser()

        btnFullbody.setOnClickListener{toRoutine(8, "Cuerpo Completo")}
        btnArms.setOnClickListener{toRoutine(1, "Brazos")}
        btnLegs.setOnClickListener{toRoutine(2, "Piernas")}
        btnChess.setOnClickListener{toRoutine(5, "Pecho")}
        btnAbdomen.setOnClickListener{toRoutine(4, "Abdomen")}
        btnBack.setOnClickListener{toRoutine(3, "Espalda")}
        apiGetRandomRoutine()
        return root
    }


    fun toRoutine(id_body_part:Int, name:String){
        SelectedClasses.bodyPart = BodyPart(id_body_part, name, null)
        println("Enviando a la rutina")
        ForFragments.replaceInFragment(routinesListFragment, fragmentManager)
    }

    private fun initRecyclerView(){
        recyclerViewRandomRoutines.adapter= RoutineFYAdapter(routineFYList, fragmentManager)
    }


    private fun verifyRoutinesAllDays(){
        val routineMonitoring = ForInternalStorageRoutineMonitoring.loadRoutineMonitoring(context)
        println("Rutina del local storage -> $routineMonitoring")
        val monday = routineMonitoring!!.doneMonday
        val tuesday = routineMonitoring.doneTuesday
        val wednesday = routineMonitoring.doneWednesday
        val thursday = routineMonitoring.doneThursday
        val friday = routineMonitoring.doneFriday
        val saturday = routineMonitoring.doneSaturday
        val sunday = routineMonitoring.doneSunday

        println("Hecho el lunes -> $monday")
        verifyRoutinesByDay(layoutMonday, iconMonday, textMonday, monday, 1)
        verifyRoutinesByDay(layoutTuesday, iconTuesday, textTuesday, tuesday, 2)
        verifyRoutinesByDay(layoutWednesday, iconWednesday, textWednesday, wednesday, 3)
        verifyRoutinesByDay(layoutThursday, iconThursday, textThursday, thursday, 4)
        verifyRoutinesByDay(layoutFriday, iconFriday, textFriday, friday, 5)
        verifyRoutinesByDay(layoutSaturday, iconSaturday, textSaturday, saturday, 6)
        verifyRoutinesByDay(layoutSunday, iconSunday, textSunday, sunday, 7)


    }

    private fun verifyRoutinesByDay(dayLayout:LinearLayout, dayIcon:ImageView, dayText:TextView,complete:Boolean?, dayWeek:Int){

        /*
        * Nombres de los layout
        * Días incompletos -> bg_day_incomplete
        * Días completos -> bg_day_complete
        * Días en proceso -> bg_day_in_progress
        *
        * Nombres de los icon
        * Días que sí se hizo una rutina -> icon_rut_completada
        * Días que no se hizo una rutina -> icon_rut_incompleta
        * En progreso -> ic_baseline_circle_24
        * */

        println("Día actual -> $dayCurrentWeek")
        println("Día pasado por parámetro -> $dayWeek")
        dayText.setTextColor(Color.BLACK)
        if (dayWeek == dayCurrentWeek) {
            dayText.setTextColor(Color.WHITE)
            dayLayout.setBackgroundResource(R.drawable.bg_day_in_progress)
            dayIcon.visibility = View.VISIBLE
            dayIcon.setImageResource(R.drawable.ic_baseline_circle_24)
            dayIcon.setPadding(10)
        } else {
            if (complete == null) {
                dayLayout.setBackgroundResource(R.drawable.bg_day_incomplete)
                dayIcon.visibility = View.INVISIBLE
            } else {
                dayLayout.setBackgroundResource(R.drawable.bg_day_complete)
                dayIcon.visibility = View.VISIBLE
                dayText.setTextColor(Color.WHITE)
                dayIcon.setPadding(0)
                if (complete){
                    dayIcon.setImageResource(R.drawable.icon_rut_completada)
                } else {
                    dayIcon.setImageResource(R.drawable.icon_rut_incompleta)
                }
            }
        }

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
                        skeleton.isVisible = true
                    }else{
                        skeleton.isVisible = false
                        initRecyclerView()
                    }
                }
            }
            override fun onFailure(call: Call<List<Routine>>, t: Throwable) {
                println("Error: random routine failed")
            }
        })

    }

    private fun checkUser(){
        val timerForCheckUser = GlobalScope.launch(Dispatchers.Main) {

            do {
                if (UserInAppCustom.user != null) {
                    routineUsername.text = UserInAppCustom.user!!.name
                    if (UserInAppCustom.user!!.image == null){
                        routineUserPhoto.setImageResource(R.drawable.profile_user_image_default)
                    } else {
                        Glide.with(root.context).load(UserInAppCustom.user!!.image?.url).into(routineUserPhoto)
                    }

                    cancel()
                }
                delay(3000)
            } while (true)
        }
    }

}