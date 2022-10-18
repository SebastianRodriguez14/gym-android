package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.databinding.ActivityRecoverPasswordCodeBinding

class RecoverPasswordCodeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRecoverPasswordCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recoverpasswordCodeEnter.setOnClickListener {

            startActivity(Intent(this, RecoverPasswordNewActivity::class.java))
        }
    }
}