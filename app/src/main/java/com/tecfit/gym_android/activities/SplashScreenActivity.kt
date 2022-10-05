package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        startTimer()
    }

    private fun startTimer() {
        object : CountDownTimer(3000, 1000){
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                val intent = Intent(applicationContext, StartActivity::class.java).apply {  }

                startActivity(intent)

            }
        }.start()
    }

}