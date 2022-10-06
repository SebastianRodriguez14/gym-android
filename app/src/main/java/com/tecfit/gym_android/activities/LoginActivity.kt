package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.R

class LoginActivity : AppCompatActivity() {

    private lateinit var text_enter:TextView
    private lateinit var txt_register:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        text_enter = findViewById(R.id.login_enter)
        txt_register = findViewById(R.id.login_enter_register)

        text_enter.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }

        txt_register.setOnClickListener {
            startActivity(Intent(applicationContext,RegisterActivity::class.java))
        }

    }
}