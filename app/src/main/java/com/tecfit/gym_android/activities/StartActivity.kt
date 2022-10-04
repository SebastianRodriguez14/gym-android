package com.tecfit.gym_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.tecfit.gym_android.R
import org.w3c.dom.Text

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