package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tecfit.gym_android.R
import com.tecfit.gym_android.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var txt_login: TextView
    private lateinit var txt_register:TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("uwu", "tmr")
        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        auth = Firebase.auth
        txt_login = findViewById(R.id.login_enter)

        txt_login.setOnClickListener{
            val email = binding.loginInputEmail.text.toString()
            val password = binding.loginInputPassword.text.toString()
            Log.i("uwu",email + " " + password )
            when{
                email.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(baseContext, "Incorrect email address or password", Toast.LENGTH_SHORT).show()
                }else -> {
                LogIn(email, password)
                }
            }
        }


        txt_register = findViewById(R.id.login_enter_register)

        txt_register.setOnClickListener {
            startActivity(Intent(applicationContext,RegisterActivity::class.java))
        }

    }
    private fun LogIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    reload()
                } else {
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Incorrect email address or password",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }
}