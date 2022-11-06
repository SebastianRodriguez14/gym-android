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
    private lateinit var breakLinear:LinearLayout
    private lateinit var crono_break:TextView
    private lateinit var btnbreak:LinearLayout
    private lateinit var namebtnbreak:TextView
    private lateinit var linearTime:LinearLayout
    private lateinit var linearRep:LinearLayout

    var m=0
    var s=0
    var ser=0
    var b=0
    var crontime=""
    var seg=""
    var min=""
    var time=0
    var serie=0
    var filled=0
    var seri=0
    var break_t=0
    var op=0
    var c1=GlobalScope.launch {  }
    var brk=GlobalScope.launch {  }

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
        btnExercise=root.findViewById(R.id.btn_exercise)
        setsFilled=root.findViewById(R.id.text_setsFilled_exercise)
        namebutton=root.findViewById(R.id.text_btn_exercise)
        sestDone=root.findViewById(R.id.text_sets_done)
        amountRep=root.findViewById(R.id.text_amount_rep)
        btnRep=root.findViewById(R.id.text_btn_exercise_rep)
        nameExercis=root.findViewById(R.id.text_name_exercise_rep)
        breakLinear=root.findViewById(R.id.linearBreak)
        crono_break=root.findViewById(R.id.text_cronodescanso)
        btnbreak=root.findViewById(R.id.btn_break)
        namebtnbreak=root.findViewById(R.id.text_btn_break)
        linearTime = root.findViewById(R.id.linearTime)
        linearRep = root.findViewById(R.id.linearRep)

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
        break_t=exercise.break_time

        val optionsd = exercise.type.id_type

        op=optionsd

        //verificando que interfaz se mostrará (cronometro o repeticiones)
        if(optionsd == 1){
            linearTime.isInvisible = true
            linearRep.isVisible= true
        }else{
            linearTime.isVisible= true
            linearRep.isInvisible= true
        }

        //iniciando ejercicio
        btnExercise.setOnClickListener(){
            btnRep.isInvisible=true
            breakLinear.isInvisible = true

            var namebtn = "En Proceso"
            namebutton.setText(namebtn)
            btnExercise.setBackgroundResource(R.drawable.shape_info_page_trainner_background)

            //ejecuando cronometro
            if (optionsd==2){
                c1=GlobalScope.launch(Dispatchers.Main){
                    while(true){
                        delay(1000)
                        aumentarSerieCronometro()

                        //finalizando cronometro
                        if(ser == serie ) {
                            c1.cancel()
                            cronometer.setText(crontime)
                            // ejericio finalizado
                            namebtn = "Completado"
                            namebutton.setText(namebtn)
                            btnExercise.setBackgroundResource(R.drawable.shape_button_reiniciar)

                            //volver a la lista de Ejercicios
                            btnExercise.setOnClickListener {
                                ForFragments.replaceInFragment(
                                    exerciseListFragment,
                                    fragmentManager
                                )
                            }
                        }
                    }
                }

            //ejecutando con repeteciciones
            }else{
                seri=1
                var setsrep=1
                sestDone.setText(seri.toString())
                    //cada que da clic en el botones de siguiente serie, aumenta la serie
                    btnRep.setOnClickListener() {
                        seri = seri + 1
                        setsrep = seri
                        sestDone.setText(setsrep.toString())
                        breakLinear.isVisible = true
                        //finalizo repeticion
                        if(seri == serie) {
                            var namebtn = "Completado"
                            nameExercis.setText(namebtn)
                            //cambiando datos de botones
                            btnExercise.setBackgroundResource(R.drawable.shape_button_reiniciar)
                            btnRep.isInvisible = true
                            namebtn = "Completado"
                            namebutton.setText(namebtn)

                            //volver a la lista ejercicios
                            btnExercise.setOnClickListener {
                                ForFragments.replaceInFragment(exerciseListFragment, fragmentManager)
                            }
                        }
                    }
                    //numero de la serie donde finalizo
                    sestDone.setText(setsrep.toString())

            }


        }

        return root
    }
    fun aumentarSerieCronometro(){
        btnExercise.isVisible=true
        ser=0
        s = s +1
        //aumentando la serie terminada
        if(s == time) {
            ser = ser + 1
            s = 0
            //descanso por serie
            linearTime.isInvisible = true
            breakLinear.isVisible = true
            breakSets()
        }
        min="00"
        seg="00"+s
        seg = seg.substring(seg.length - 2, seg.length)
        crontime = min+":"+seg
        cronometer.setText(crontime)

        filled = ser
        setsFilled.setText(filled.toString())
    }

    fun breakSets(){
        btnExercise.isInvisible=true
        btnbreak.setOnClickListener{
            brk= GlobalScope.launch(Dispatchers.Main) {
                while(true){
                    delay(1000)
                    b=b+1
                    min="00"
                    seg="00"+b
                    seg = seg.substring(seg.length - 2, seg.length)
                    crontime = min+":"+seg
                    crono_break.setText(crontime)
                    btnbreak.isInvisible=true

                    if(b == break_t){
                        brk.cancel()
                        btnbreak.isVisible=true
                        var name= "Continuar"
                        namebtnbreak.setText(name)
                            if(op==2){
                                btnbreak.setOnClickListener {
                                    linearTime.isVisible = true
                                    breakLinear.isInvisible = true
                                }
                            }
                        }
                    crono_break.setText(b.toString())
                    }
                }
            }
        }
    }
