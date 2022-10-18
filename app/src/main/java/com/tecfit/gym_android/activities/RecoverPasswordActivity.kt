package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.databinding.ActivityRecoverPasswordEmailBinding

class RecoverPasswordActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRecoverPasswordEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recoverpasswordEnter.setOnClickListener {
            val email = binding.recoverpasswordInputEmail.text.toString()

            if (email.isEmpty()) {
                binding.errorRecoverEmail.visibility =
                    if (email.isEmpty()) View.VISIBLE else View.INVISIBLE
            } else {
                startActivity(Intent(this, RecoverPasswordCodeActivity::class.java))
            }
        }

    }
}