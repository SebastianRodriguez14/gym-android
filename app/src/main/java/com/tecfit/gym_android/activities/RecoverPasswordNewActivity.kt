package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tecfit.gym_android.databinding.ActivityRecoverPasswordNewBinding

class RecoverPasswordNewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecoverPasswordNewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordNewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recoverpasswordNewEnter.setOnClickListener {
            val passwordone = binding.recoverpasswordNewInputPassword.text.toString()
            val passwordtwo = binding.recoverpasswordNewInputPasswordTwo.text.toString()

            if (passwordone.isEmpty()|| passwordtwo.isEmpty()){
                binding.errorMessagePasswordRecover.visibility =if (passwordone.isEmpty()) View.VISIBLE else View.INVISIBLE
                binding.errorMessagePasswordTwoRecover.visibility =if (passwordtwo.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
            else{
                startActivity(Intent(this,LoginActivity::class.java))
            }
        }

        }

    }