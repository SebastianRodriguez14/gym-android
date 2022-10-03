package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.telecom.Call
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.fragments.adapter.TrainerAdapter
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.Trainer
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Response
import java.net.CacheResponse
import javax.security.auth.callback.Callback

class InfoPageFragment : Fragment() {


    private lateinit var text_ubication:TextView
    private lateinit var text_whatsapp:TextView
    private lateinit var text_facebook:TextView
    private lateinit var text_yape:TextView
    // Por defecto tiene el id de la opción de ubicación
    private var id_text_selected: Int? = 2131296748

    //Elementos de la información de la empresa
    private lateinit var text_data:TextView
    private lateinit var image_data:ImageView

    private val trainersList = listOf<Trainer>(
        Trainer("Owen Antony","Sanchez Peratta","Soy preparador físico personal especializado en entrenamientos con objetivos especificos de acuerdo a los objetivos personales del alumno.Tonificacion y disminucion de peso, aumento de masa muscular, rutinas post lesiones y nutrición deportiva.","https://res.cloudinary.com/dsh0qgcpn/image/upload/v1664821274/entrenador1_1_gukf6w.png"),
        Trainer("Denzel","Ramos Rodriguez","Soy Entrenador Personal y Preparador Físico; programo rutinas individuales, grupales y sesiones de entrenamiento de acuerdo al nivel físico de cada persona; y si estás a un nivel principiante, intermedio o avanzado tengo una rutina adecuada para cada uno.","https://res.cloudinary.com/dsh0qgcpn/image/upload/v1664821274/entrenador1_1_gukf6w.png")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_info_page, container, false)

        text_ubication = root.findViewById(R.id.text_location)
        text_facebook = root.findViewById(R.id.text_facebook)
        text_whatsapp = root.findViewById(R.id.text_whatsapp)
        text_yape = root.findViewById(R.id.text_yape)

        text_data = root.findViewById(R.id.info_page_data_text)
        image_data = root.findViewById(R.id.info_page_data_image)

        val arrayOptions = arrayOf<TextView>(text_ubication, text_facebook, text_whatsapp, text_yape)

        //Seteamos la ubicación como opción marcada por defecto
        setBackgroundSelected(arrayOptions, text_ubication)

        text_ubication.setOnClickListener { setBackgroundSelected(arrayOptions, text_ubication) }
        text_facebook.setOnClickListener { setBackgroundSelected(arrayOptions, text_facebook) }
        text_whatsapp.setOnClickListener { setBackgroundSelected(arrayOptions, text_whatsapp) }
        text_yape.setOnClickListener { setBackgroundSelected(arrayOptions, text_yape) }

        initRecyclerView(root,R.id.recyclerview_trainers)
        apiGetTrainers()
        return root
    }

    private fun setBackgroundSelected (arrayTextViews: Array<TextView>, text: TextView) {

        for(textview in arrayTextViews){
            if (textview.id == text.id){
                textview.setBackgroundResource(R.drawable.shape_info_page_option_selected)
                id_text_selected = text.id

                when(text.id){
                    2131296748 -> {
                        text_data.setText(R.string.info_page_data_location)
                        image_data.setImageResource(R.drawable.info_page_data_location)
                    }
                    2131296747 -> {
                        text_data.setText(R.string.info_page_data_facebook)
                        image_data.setImageResource(R.drawable.info_page_data_facebook)
                    }
                    2131296749 -> {
                        text_data.setText(R.string.info_page_data_whatsapp)
                        image_data.setImageResource(R.drawable.info_page_data_whatsapp)
                    }
                    2131296750 -> {
                        text_data.setText(R.string.info_page_data_yape)
                        image_data.setImageResource(R.drawable.info_page_data_yape)
                    }
                }
            } else{
                textview.setBackgroundResource(R.drawable.shape_info_page_option)
            }
            println(textview.text.toString() + " - " + textview.id)
        }

    }

    private fun initRecyclerView(view: View,id:Int){
        val recyclerView=view.findViewById<RecyclerView>(id)
        recyclerView.layoutManager=LinearLayoutManager(view.context)
        recyclerView.adapter= TrainerAdapter(trainersList)
    }

    private fun apiGetTrainers(){
        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultTrainers: retrofit2.Call<List<Trainer>> = apiService.getTrainers()

        resultTrainers.enqueue(object : retrofit2.Callback<List<Trainer>> {
            override fun onResponse(call: retrofit2.Call<List<Trainer>>, response: Response<List<Trainer>>){
                val listTrainers = response.body()
                if (listTrainers != null){
                    for (tr in listTrainers){
                        println("Nombre"+tr.name)
                    }
                }
            }
            override fun onFailure(call:retrofit2.Call<List<Trainer>>, t:Throwable){
                println("Error: getTrainers() failure")
            }
        })

    }
}