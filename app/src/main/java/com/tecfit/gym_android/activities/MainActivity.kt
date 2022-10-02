package com.tecfit.gym_android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.tecfit.gym_android.fragments.ProductActivity
import com.tecfit.gym_android.R
class MainActivity : AppCompatActivity() {

    private val productsFragment = ProductActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        replaceFragment(productsFragment)
        setContentView(R.layout.activity_main)
    }

    private fun replaceFragment(fragment:Fragment){
        val fragmentManager = supportFragmentManager

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_products, fragment)
        fragmentTransaction.commit()
    }

}