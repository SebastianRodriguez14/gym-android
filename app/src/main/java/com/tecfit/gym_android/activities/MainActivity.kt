package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
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
import com.tecfit.gym_android.fragments.ProfileUserFragment
import com.tecfit.gym_android.fragments.RoutinesFragment

class MainActivity : AppCompatActivity() {
    // Fragmentos principales
    private val infoPageFragment = InfoPageFragment()
    private val productFragment = ProductFragment()
    private val routinesFragment = RoutinesFragment()
    private val profileUserFragment = ProfileUserFragment()

    private lateinit var meowBottomNavigation:MeowBottomNavigation

    private val ID_INFORMATION = 1;
    private val ID_PRODUCTS = 2;
    private val ID_ROUTINES = 3;
    private val ID_PROFILE = 4;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        ForFragments.replaceFragment(supportFragmentManager,R.id.frame_container, infoPageFragment)
        meowBottomNavigation = findViewById(R.id.bottom_navigation)
        meowBottomNavigation.add(MeowBottomNavigation.Model(ID_INFORMATION, R.drawable.ic_baseline_home_24))
        meowBottomNavigation.add(MeowBottomNavigation.Model(ID_PRODUCTS, R.drawable.ic_baseline_shopping_cart_24))
        meowBottomNavigation.add(MeowBottomNavigation.Model(ID_ROUTINES, R.drawable.ic_baseline_sports_gymnastics_24))
        meowBottomNavigation.add(MeowBottomNavigation.Model(ID_PROFILE, R.drawable.ic_baseline_person_24))

        meowBottomNavigation.setOnClickMenuListener {
            when(it.id){
                ID_INFORMATION -> ForFragments.replaceFragment(supportFragmentManager,R.id.frame_container, infoPageFragment)
                ID_PRODUCTS -> ForFragments.replaceFragment(supportFragmentManager, R.id.frame_container,productFragment)
                ID_ROUTINES -> ForFragments.replaceFragment(supportFragmentManager, R.id.frame_container,routinesFragment)
                ID_PROFILE -> ForFragments.replaceFragment(supportFragmentManager, R.id.frame_container, profileUserFragment)
            }
        }
        meowBottomNavigation.show(ID_INFORMATION)

    }
}