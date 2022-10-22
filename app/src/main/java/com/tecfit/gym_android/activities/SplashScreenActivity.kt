package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.R
import kotlinx.coroutines.*

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startTimer()
    }

    private fun startTimer() {
        val timer = GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            startActivity(Intent(applicationContext, StartActivity::class.java))
            finish()
        }
    }

}