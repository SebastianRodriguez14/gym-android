package com.tecfit.gym_android.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.logout
import com.tecfit.gym_android.databinding.FragmentProfileUserBinding
import com.tecfit.gym_android.models.User
import com.tecfit.gym_android.models.UserData
import java.util.concurrent.Executors

class ProfileUserFragment : Fragment() {
    private lateinit var logOut: TextView
    private lateinit var photo: ImageView
    private lateinit var name: TextView
    private lateinit var membership: LinearLayout
    private lateinit var phone: EditText
    private lateinit var root:View

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
        phone = root.findViewById(R.id.profile_user_input_phone)
        photo = root.findViewById(R.id.photo_profile)

        logOut = root.findViewById(R.id.profile_user_logout_link)
        logOut.setOnClickListener{
            context?.logout()
        }

        name.text = UserData.user.name + ' ' + UserData.user.lastname
        phone.setText(UserData.user.phone)
        membership.isVisible = UserData.user.membership
        Glide.with(root.context).load(UserData.user.image?.url).into(photo)
        println(UserData.user.name)

        return root

    }


}