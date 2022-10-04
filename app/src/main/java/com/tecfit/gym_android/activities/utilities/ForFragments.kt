package com.tecfit.gym_android.activities.utilities

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class ForFragments {

    companion object{
        fun replaceFragment(fragmentManager: FragmentManager, container:Int, fragment: Fragment){
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(container, fragment)
            fragmentTransaction.commit()
        }
    }

}