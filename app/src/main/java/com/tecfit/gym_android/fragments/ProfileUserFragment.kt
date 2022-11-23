package com.tecfit.gym_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.switchmaterial.SwitchMaterial
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForInternalStorageNotification
import com.tecfit.gym_android.activities.utilities.ForInternalStorageUser
import com.tecfit.gym_android.activities.utilities.ForNotifications
import com.tecfit.gym_android.activities.utilities.logout
import com.tecfit.gym_android.databinding.FragmentProfileUserBinding
import com.tecfit.gym_android.models.custom.UserInAppCustom
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class ProfileUserFragment : Fragment() {
    private lateinit var logOut: TextView
    private lateinit var photo: ImageView
    private lateinit var name: TextView
    private lateinit var membership: LinearLayout
    private lateinit var phone: EditText
    private lateinit var root:View
    private lateinit var switch:SwitchMaterial
    private lateinit var inputMembership: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_profile_user, container, false)
        var binding : FragmentProfileUserBinding

        name = root.findViewById(R.id.profile_user_name)
        membership = root.findViewById(R.id.profile_user_active_membership)
        membership.isVisible = false
        phone = root.findViewById(R.id.profile_user_input_phone)
        photo = root.findViewById(R.id.photo_profile)
        switch = root.findViewById(R.id.profile_user_switch_notification)
        inputMembership = root.findViewById(R.id.profile_user_input_membership)
        logOut = root.findViewById(R.id.profile_user_logout_link)
        logOut.setOnClickListener{
            context?.logout()
            UserInAppCustom.user = null
            ForInternalStorageUser.saveUser(null, context)
            ForInternalStorageNotification.disableNotification(context)
            ForInternalStorageNotification.changeStateNotification(context, false)
        }

        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                ForInternalStorageNotification.activeNotification(context)
                ForNotifications.sendNotification(context)
            } else {
                ForInternalStorageNotification.disableNotification(context)
            }
            ForInternalStorageNotification.changeStateNotification(context, isChecked)

        }

        checkUser()
        return root

    }

    fun setInformationUser(){

        name.text = UserInAppCustom.user!!.name + ' ' + UserInAppCustom.user!!.lastname
        phone.setText(UserInAppCustom.user!!.phone)
        membership.isVisible = UserInAppCustom.user!!.membership
        if (UserInAppCustom.user!!.image?.url != null) {
            Glide.with(root.context).load(UserInAppCustom.user!!.image?.url).into(photo)
        } else {
            photo.setBackgroundResource(R.drawable.profile_user_image_default)
        }



    }

    fun setInformationMembership(){

        val existMembership:Boolean
        if (UserInAppCustom.membership!!.id_membership == 0){
            inputMembership.setText("Sin membresía")
            existMembership = false
        } else {
            val currentDate = Date()

            val time_elapsed:Long = UserInAppCustom.membership!!.expiration_date.time - currentDate.time

            val unit = TimeUnit.DAYS

            val days = unit.convert(time_elapsed, TimeUnit.MILLISECONDS) //Días restantes


            val remainingDays = formatRemainingDays(days)

            inputMembership.setText(remainingDays)
            switch.isEnabled = true
            val activeNotification = ForInternalStorageNotification.loadStateNotification(context)
            if (activeNotification!= null){
                switch.isChecked = activeNotification
            }
            existMembership = true
        }

        UserInAppCustom.user!!.membership = existMembership

    }

    fun formatRemainingDays(days:Long):String {
        var remainingDays = ""
//        println( "Cantidad de días -> $days")
//        println(days/30)
        if (days>30L){
            remainingDays += "${days/30} mes y ${days%30} días"
        } else if (days == 30L ) {
            remainingDays += "${days/30} mes"
        } else {
            remainingDays += "${days%30} días"
        }

        return remainingDays

    }

    fun checkUser(){

        val timerForCheckUser = GlobalScope.launch(Dispatchers.Main) {

            do {
                if (UserInAppCustom.user != null) {
                    setInformationUser()

                    cancel()
                }
                delay(3000)
            } while (true)
        }

        val timerForCheckMembership = GlobalScope.launch(Dispatchers.Main){

            do {

                if (UserInAppCustom.membership != null) {

                    setInformationMembership()

                    cancel()
                }

                delay(3000)

            } while (true)

        }

    }

}