package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.R

class RegisterActivity : AppCompatActivity() {
    private lateinit var txtenter:TextView
    private lateinit var txt_login:TextView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtenter = findViewById(R.id.register_enter)
        txt_login = findViewById(R.id.register_enter_login)

        txtenter.setOnClickListener{
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }

        txt_login.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }

    }


}