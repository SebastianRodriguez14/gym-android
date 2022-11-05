package com.tecfit.gym_android.fragments

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
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.fragments.adapter.ExerciseAdapter
import com.tecfit.gym_android.models.Exercise
import com.tecfit.gym_android.models.custom.ArraysForClass
import com.tecfit.gym_android.models.custom.SelectedClasses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text

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
    private lateinit var setsFilled:TextView
    private lateinit var namebutton:TextView
    private lateinit var sestDone:TextView
    private lateinit var amountRep:TextView
    private lateinit var btnRep:LinearLayout
    private lateinit var nameExercis:TextView

    var m=0
    var s=0
    var ser=0
    var crontime=""
    var seg=""
    var min=""
    var time=0
    var serie=0
    var filled=0
    var seri=0

    var c1=GlobalScope.launch {  }

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
        cronometer=root.findViewById(R.id.cronometer)
        btnExercise=root.findViewById(R.id.btn_exercise)
        setsFilled=root.findViewById(R.id.text_setsFilled_exercise)
        namebutton=root.findViewById(R.id.text_btn_exercise)
        sestDone=root.findViewById(R.id.text_sets_done)
        amountRep=root.findViewById(R.id.text_amount_rep)
        btnRep=root.findViewById(R.id.text_btn_exercise_rep)
        nameExercis=root.findViewById(R.id.text_name_exercise_rep)

        val exerciseListFragment = ExerciseListFragment()

        imageBackToList.setOnClickListener{ForFragments.replaceInFragment(exerciseListFragment,fragmentManager)}

        exercise = Exercise(SelectedClasses.exercise.id_exercise,SelectedClasses.exercise.name, SelectedClasses.exercise.file,SelectedClasses.exercise.type,SelectedClasses.exercise.break_time,SelectedClasses.exercise.amount,SelectedClasses.exercise.sets)
        nameexercise.text=exercise.name
        setsexercise.text= "Series: ${exercise.sets}"
        breakexercise.text= "Descanso: ${exercise.break_time} seg."
        amountexercise.text = "Tiempo: ${exercise.amount} seg."
        amountRep.text="${exercise.amount} repeticiones"
        time = exercise.amount
        serie=exercise.sets

        val optionsd = exercise.type.id_type
        var linearTime:LinearLayout = root.findViewById(R.id.linearTime)
        var linearRep:LinearLayout = root.findViewById(R.id.linearRep)

        if(optionsd == 1){
            linearTime.isInvisible = true
            linearRep.isVisible= true
        }else{
            linearTime.isVisible= true
            linearRep.isInvisible= true
        }

        btnExercise.setOnClickListener(){
            btnRep.isInvisible=true
            var namebtn = "En Proceso"
            namebutton.setText(namebtn)
            btnExercise.setBackgroundResource(R.drawable.shape_info_page_trainner_background)
            if (optionsd==2){
                c1=GlobalScope.launch(Dispatchers.Main){
                    while(true){
                        delay(1000)
                        s = s +1
                        if(s == time){
                            ser = ser + 1
                            s = 0
                        }
                        min="00"
                        seg="00"+s
                        seg = seg.substring(seg.length - 2, seg.length)
                        crontime = min+":"+seg
                        cronometer.setText(crontime)

                        filled = ser
                        setsFilled.setText(filled.toString())

                        if(ser == serie ){
                            c1.cancel()
                            cronometer.setText(crontime)
                            namebtn = "Completado"
                            namebutton.setText(namebtn)
                            btnExercise.setBackgroundResource(R.drawable.shape_button_reiniciar)
                            btnExercise.setOnClickListener {
                                ForFragments.replaceInFragment(exerciseListFragment, fragmentManager)
                            }

                        }
                    }
                }
            }else{
                btnRep.isVisible=true
                seri=1
                var setsrep=1
                sestDone.setText(seri.toString())


                    btnRep.setOnClickListener() {
                        seri = seri + 1
                        setsrep = seri
                        sestDone.setText(setsrep.toString())
                        if(seri == serie) {
                            var namebtn = "Completado"
                            nameExercis.setText(namebtn)
                            btnExercise.setBackgroundResource(R.drawable.shape_button_reiniciar)
                            btnRep.isInvisible = true
                            namebtn = "Completado"
                            namebutton.setText(namebtn)
                            btnExercise.setOnClickListener {
                                ForFragments.replaceInFragment(exerciseListFragment, fragmentManager)
                            }
                        }
                    }
                    sestDone.setText(setsrep.toString())



            }

        }

        return root
    }



}