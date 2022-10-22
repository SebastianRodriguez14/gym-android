package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tecfit.gym_android.databinding.ActivityRecoverPasswordEmailBinding

class RecoverPasswordActivity : AppCompatActivity(){
    private lateinit var binding: ActivityRecoverPasswordEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth

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
               sendPasswordReset(email)
            }
        }

        firebaseAuth = Firebase.auth

    }
    fun sendPasswordReset(email : String){
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener() { task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, VerifyEmailActivity::class.java))
                }else{
                    Toast.makeText(this, "La dirección de correo electronico debe ser valida", Toast.LENGTH_SHORT).show()
                }
            }
    }
}