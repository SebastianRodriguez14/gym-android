package com.tecfit.gym_android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tecfit.gym_android.R
import com.tecfit.gym_android.fragments.InfoPageFragment

class MainActivity : AppCompatActivity() {
    // Fragmentos principales
    private val infoPageFragment = InfoPageFragment()

    //BottomNavigation
    private lateinit var bottomNavigation:BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigation = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener {
            println("asdas")
            when(it.itemId){
                R.id.item_tec_fit -> replaceFragment(infoPageFragment)
            }
            true
        }

    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_container, fragment)
        fragmentTransaction.commit()
    }


}