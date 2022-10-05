package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.R

class StartActivity : AppCompatActivity() {

    private lateinit var text_start:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        text_start = findViewById(R.id.text_start)

        text_start.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }




    }
}