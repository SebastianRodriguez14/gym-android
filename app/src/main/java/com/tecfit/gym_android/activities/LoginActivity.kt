package com.tecfit.gym_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tecfit.gym_android.R

class LoginActivity : AppCompatActivity() {

    private lateinit var fl: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        fl = findViewById(R.id.login_forgot_password)

        fl.setOnClickListener{
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}