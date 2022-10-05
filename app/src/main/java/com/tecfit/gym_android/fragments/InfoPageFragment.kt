package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.fragments.adapter.TrainerAdapter
import com.tecfit.gym_android.models.Trainer
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class InfoPageFragment : Fragment() {


    private lateinit var text_ubication:TextView
    private lateinit var text_whatsapp:TextView
    private lateinit var text_facebook:TextView
    private lateinit var text_yape:TextView
    // Por defecto tiene el id de la opción de ubicación
    private lateinit var text_selected: TextView

    //Elementos de la información de la empresa
    private lateinit var text_data:TextView
    private lateinit var image_data:ImageView


    //Frame contenedor para los bg del yape, ubicación, facebook y whatsapp
    private lateinit var frame_container_data_bg:FrameLayout

    //Fragmentos de yape, ubicación, facebook y whatsapp
    private val infoPageYapeFragment = InfoPageYapeFragment()

    private lateinit var trainersList:List<Trainer>

    //Vista raíz
    private lateinit var root:View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        root = inflater.inflate(R.layout.fragment_info_page, container, false)

        text_ubication = root.findViewById(R.id.text_location)
        text_facebook = root.findViewById(R.id.text_facebook)
        text_whatsapp = root.findViewById(R.id.text_whatsapp)
        text_yape = root.findViewById(R.id.text_yape)

        text_selected = text_ubication

        text_data = root.findViewById(R.id.info_page_data_text)
        image_data = root.findViewById(R.id.info_page_data_image)

        frame_container_data_bg = root.findViewById(R.id.frame_data_container_info_page)

        val arrayOptions = arrayOf<TextView>(text_ubication, text_facebook, text_whatsapp, text_yape)

        //Seteamos la ubicación como opción marcada por defecto
        setBackgroundSelected(arrayOptions, text_ubication)

        text_ubication.setOnClickListener { setBackgroundSelected(arrayOptions, text_ubication) }
        text_facebook.setOnClickListener { setBackgroundSelected(arrayOptions, text_facebook) }
        text_whatsapp.setOnClickListener { setBackgroundSelected(arrayOptions, text_whatsapp) }
        text_yape.setOnClickListener { setBackgroundSelected(arrayOptions, text_yape) }


        apiGetTrainers()
        return root
    }

    private fun setBackgroundSelected (arrayTextViews: Array<TextView>, text: TextView) {

        for(textview in arrayTextViews){
            if (textview == text){
                textview.setBackgroundResource(R.drawable.shape_info_page_option_selected)
                text_selected = text

                when(text){
                    text_ubication -> {
                        text_data.setText(R.string.info_page_data_location)
                        image_data.setImageResource(R.drawable.info_page_data_location)

                    }
                    text_facebook -> {
                        text_data.setText(R.string.info_page_data_facebook)
                        image_data.setImageResource(R.drawable.info_page_data_facebook)
                    }
                    text_whatsapp -> {
                        text_data.setText(R.string.info_page_data_whatsapp)
                        image_data.setImageResource(R.drawable.info_page_data_whatsapp)
                    }
                    text_yape -> {
                        text_data.setText(R.string.info_page_data_yape)
                        image_data.setImageResource(R.drawable.info_page_data_yape)
                        ForFragments.replaceFragment(childFragmentManager,frame_container_data_bg.id, infoPageYapeFragment)
                    }
                }
            } else{
                textview.setBackgroundResource(R.drawable.shape_info_page_option)
            }
            println(textview.text.toString() + " - " + textview.id)
        }

    }


    private fun initRecyclerView(id:Int){
        val recyclerView=root.findViewById<RecyclerView>(id)
        recyclerView.layoutManager=LinearLayoutManager(root.context)
        recyclerView.adapter= TrainerAdapter(trainersList)
    }

    private fun apiGetTrainers(){
        val apiService:ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val resultTrainers: Call<List<Trainer>> = apiService.getTrainers()

        resultTrainers.enqueue(object : Callback<List<Trainer>> {
            override fun onResponse(call: Call<List<Trainer>>, response: Response<List<Trainer>>){
                val listTrainers = response.body()
                if (listTrainers != null){
                    trainersList = listTrainers
                    initRecyclerView(R.id.recyclerview_trainers)
                }
            }
            override fun onFailure(call:Call<List<Trainer>>, t:Throwable){
                println("Error: getTrainers() failure")
                println(t.message)
            }
        })

    }
}