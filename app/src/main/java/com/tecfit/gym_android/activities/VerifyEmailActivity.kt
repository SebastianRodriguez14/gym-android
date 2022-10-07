package com.tecfit.gym_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tecfit.gym_android.R
import com.tecfit.gym_android.databinding.ActivityMainBinding
import com.tecfit.gym_android.databinding.ActivityVerifyEmailBinding

class VerifyEmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verifyEmailAccessLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }


    }
}