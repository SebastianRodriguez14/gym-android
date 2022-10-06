package com.tecfit.gym_android.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForFragments
import com.tecfit.gym_android.databinding.ActivityLoginBinding
import com.tecfit.gym_android.databinding.ActivityStartBinding
import com.tecfit.gym_android.fragments.InfoPageFragment
import com.tecfit.gym_android.fragments.ProductFragment

class MainActivity : AppCompatActivity() {
    // Fragmentos principales
    private val infoPageFragment = InfoPageFragment()
    private val productFragment = ProductFragment()
    private lateinit var auth: FirebaseAuth

    //BottomNavigation
    private lateinit var bottomNavigation:BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth

        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.item_tec_fit -> ForFragments.replaceFragment(supportFragmentManager,R.id.frame_container, infoPageFragment)
                R.id.item_productos -> ForFragments.replaceFragment(supportFragmentManager, R.id.frame_container,productFragment)
            }
            true
        }

    }



}