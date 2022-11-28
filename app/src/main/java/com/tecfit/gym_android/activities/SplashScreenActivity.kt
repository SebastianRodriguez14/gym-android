package com.tecfit.gym_android.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForInternalStorageRoutineMonitoring
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var context:Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        context = this
        ForInternalStorageRoutineMonitoring.saveRoutineMonitoring(context)
        startTimer()
    }

    private fun startTimer() {
        val timer = GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            startActivity(Intent(applicationContext, StartActivity::class.java))
            finish()
        }

        // Pruebitas para el monitoreo 🤙


    }

}