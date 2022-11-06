package com.tecfit.gym_android.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.custom.SelectedClasses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseFragment : Fragment(){

    private lateinit var root:View
    private lateinit var nameexercise:TextView
    private lateinit var imageBackToList:ImageView
    private lateinit var exercise:Exercise
    private lateinit var setsexercise:TextView
    private lateinit var breakexercise:TextView
    private lateinit var videoexercise:VideoView
    private lateinit var amountexercise:TextView
    private lateinit var cronometer:TextView

    private lateinit var btnExercise:LinearLayout
    private lateinit var btnRepetition:LinearLayout
    private lateinit var btnbreak:LinearLayout

    private lateinit var namebtnExercise:TextView
    private lateinit var namebtnbreak:TextView

    private lateinit var setsDoing:TextView

    private lateinit var sestDoneRepetition:TextView
    private lateinit var amountRepetitions:TextView
    private lateinit var crono_break:TextView

    private lateinit var linearCronometer:LinearLayout
    private lateinit var linearRep:LinearLayout
    private lateinit var linearBreak:LinearLayout

    private var exerciseListFragment=ExerciseListFragment()

    var time=0
    var setsExercise=0
    var break_tiempo=0

    var s=0
    var setsCronometer_doing=-1
    var setsRepetitions_doing=-1

    //visualizar en 00:00
    var crontime=""
    var seconds=""
    var minutes=""

    var optionTypeExercise=0
    var coroutineExercises=GlobalScope.launch {  }
    var coroutinesBreak=GlobalScope.launch {  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_exercise, container, false)

        nameexercise= root.findViewById(R.id.text_name_exercise)
        imageBackToList = root.findViewById(R.id.exercise_list_body_part_back)
        setsexercise = root.findViewById(R.id.text_sets_exercise)
        breakexercise=root.findViewById(R.id.text_break_exercise)
        videoexercise=root.findViewById(R.id.video_exercise)
        amountexercise=root.findViewById(R.id.text_amount_exercise)
        cronometer=root.findViewById(R.id.text_cronometer)

        linearCronometer = root.findViewById(R.id.linearTime)
        linearRep = root.findViewById(R.id.linearRep)
        linearBreak=root.findViewById(R.id.linearBreak)

        btnExercise=root.findViewById(R.id.btn_exercise)
        btnRepetition=root.findViewById(R.id.text_btn_exercise_rep)
        btnbreak=root.findViewById(R.id.btn_break)
        //nombre de los botones
        namebtnExercise=root.findViewById(R.id.text_btn_exercise)
        namebtnbreak=root.findViewById(R.id.text_btn_break)

        //numero de serie que esta realizando
        setsDoing=root.findViewById(R.id.text_setsDoing_exercise)

        //num de serie realizadas
        sestDoneRepetition=root.findViewById(R.id.text_sets_done_rep)
        //num de repeticiones que debe realizar
        amountRepetitions=root.findViewById(R.id.text_amount_rep)

        //cronometro de descanso
        crono_break=root.findViewById(R.id.text_cronodescanso)

        val exerciseListFragment = ExerciseListFragment()

        imageBackToList.setOnClickListener{ForFragments.replaceInFragment(exerciseListFragment,fragmentManager)}

        exercise = Exercise(SelectedClasses.exercise.id_exercise,SelectedClasses.exercise.name, SelectedClasses.exercise.file,SelectedClasses.exercise.type,SelectedClasses.exercise.break_time,SelectedClasses.exercise.amount,SelectedClasses.exercise.sets)
        nameexercise.text=exercise.name
        setsexercise.text= "Series: ${exercise.sets}"
        breakexercise.text= "Descanso: ${exercise.break_time} seg."
        amountexercise.text = "Tiempo: ${exercise.amount} seg."
        amountRepetitions.text="${exercise.amount} repeticiones"
        time = exercise.amount
        setsExercise=exercise.sets
        break_tiempo=exercise.break_time

        val optionsd = exercise.type.id_type
        optionTypeExercise=optionsd

        verifyingTypeExercise()

        //iniciando ejercicio
        btnExercise.setOnClickListener(){
            initExercise()
            namebtnExercise.setText(R.string.exercise_button_process)
            btnExercise.setBackgroundResource(R.drawable.shape_info_page_trainner_background)
            btnExercise.isEnabled=false
        }

        return root
    }

    private fun verifyingTypeExercise() {
        btnExercise.isVisible=true
        linearBreak.isInvisible=true

        //verificando que interfaz se debe mostrar(cronometro o repeticiones)
        if(optionTypeExercise == 1){
            linearCronometer.isInvisible = true
            linearRep.isVisible= true
        }else{
            linearCronometer.isVisible= true
            linearRep.isInvisible= true
        }
    }

    private fun initExercise(){
        if(optionTypeExercise == 1) {
            initRepetitions()
        }else{
            initChronometer()
        }
    }

    private fun initRepetitions() {
        btnRepetition.isVisible = true
        btnExercise.isVisible = true

        setsRepetitions_doing = setsRepetitions_doing + 1
        sestDoneRepetition.setText(setsRepetitions_doing.toString())

        btnRepetition.setOnClickListener() {
            //verificando las series
            if (setsRepetitions_doing != setsExercise) {
                breakSets()
            }else {
                buttonsFinality()
            }
            //numero de la serie donde finalizo
            sestDoneRepetition.setText(setsRepetitions_doing.toString())
        }
    }

    private fun initChronometer() {
        s = 0
        linearCronometer.isVisible = true
        setsCronometer_doing += 1
        setsDoing.setText(setsCronometer_doing.toString())

        if (setsCronometer_doing != setsExercise) {
            coroutineExercises = GlobalScope.launch(Dispatchers.Main) {
                running_Cronometro()
            }
        }else {
            crontime="00:00"
            cronometer.setText(crontime)
            buttonsFinality()
        }

    }

    private suspend fun running_Cronometro() {
        while (true) {
            delay(1000)
            linearCronometer.isVisible = true
            btnExercise.isVisible = true
            s+=1
            if (s == time) {
                s = 0
                linearCronometer.isInvisible = true
                if(setsCronometer_doing != setsExercise-1){
                    breakSets()
                }else{
                    linearBreak.isVisible = true
                    initChronometer()
                }
                break
            }
            minutes = "00"
            seconds = "00" + s
            seconds = seconds.substring(seconds.length - 2, seconds.length)
            crontime = minutes + ":" + seconds
            cronometer.setText(crontime)
        }
    }

    private fun breakSets(){
        var segBreak=""
        var minBreak=""
        linearBreak.isVisible = true
        btnExercise.isInvisible=true
        visibilitylinearBreakExerciso()

        btnbreak.setOnClickListener{
            var cronoBreak=""
            var timebreakSeconds=0
            coroutinesBreak= GlobalScope.launch(Dispatchers.Main) {
                while(true){
                    delay(1000)
                    timebreakSeconds += 1
                    btnbreak.isInvisible=true
                    if(timebreakSeconds == break_tiempo+1){
                        coroutinesBreak.cancel()
                        timebreakSeconds=0
                        btnbreak.isVisible=true
                        namebtnbreak.setText(R.string.exercise_break_to_repetitions)
                        btnbreak.setOnClickListener { verifyingTypeExercise()
                            initExercise() }
                    }
                    minBreak="00"
                    segBreak="00"+timebreakSeconds
                    segBreak = segBreak.substring(segBreak.length - 2, segBreak.length)
                    cronoBreak = minBreak+":"+segBreak
                    crono_break.setText(cronoBreak)

                }
            }
        }
    }

    private fun buttonsFinality(){
        linearBreak.isInvisible = true
        btnExercise.setBackgroundResource(R.drawable.shape_button_reiniciar)
        btnExercise.setOnClickListener { ForFragments.replaceInFragment(exerciseListFragment, fragmentManager) }
        btnExercise.isEnabled=true
        namebtnExercise.setText(R.string.exercise_button_filled)

        if(optionTypeExercise == 1) {
            buttonsFinalityRep()
        }else{
            buttonsFinalityCrono()
        }
    }

    private fun buttonsFinalityRep() {
        btnRepetition.isInvisible = true
    }
    private fun buttonsFinalityCrono(){
        //
    }

    private fun visibilitylinearBreakExerciso(){
        if(optionTypeExercise == 1) {
            linearRep.isInvisible=true
        }else{
            linearCronometer.isInvisible=true
        }
    }
}


