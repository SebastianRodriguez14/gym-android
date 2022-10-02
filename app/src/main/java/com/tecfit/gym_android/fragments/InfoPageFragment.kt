package com.tecfit.gym_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tecfit.gym_android.R

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

}