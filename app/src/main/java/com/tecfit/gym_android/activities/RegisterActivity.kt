package com.tecfit.gym_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.tecfit.gym_android.databinding.ActivityRegisterBinding
import com.tecfit.gym_android.models.User
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.registerEnter.setOnClickListener{

            if (checkInputsCompleted()){
                registerUserFirebase()
            }


        }

        binding.registerEnterLogin.setOnClickListener {
            startActivity(Intent(applicationContext,LoginActivity::class.java))
        }

    }

    private fun checkInputsCompleted():Boolean{

        val checks = arrayOf(
            checkInput(binding.registerInputName, binding.errorMessageNameRegister),
            checkInput(binding.registerInputLastname, binding.errorMessageLastnameRegister),
            checkInput(binding.registerInputPhone, binding.errorMessagePhoneRegister),
            checkInput(binding.registerInputEmail, binding.errorMessageEmailRegister),
            checkInput(binding.registerInputPassword, binding.errorMessagePasswordRegister)
        )

        return !checks.contains(true)

    }

    private fun checkInput(input:EditText, errorMessage:TextView):Boolean {
        val showError = input.text.toString().isEmpty()

        errorMessage.visibility = if (showError) View.VISIBLE else View.INVISIBLE
        return showError
    }



    private fun registerUserFirebase(){

        val email = binding.registerInputEmail.text.toString()
        val password = binding.registerInputPassword.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful) {

                auth.currentUser?.sendEmailVerification()

                apiPostUser()

                startActivity(Intent(applicationContext,VerifyEmailActivity::class.java))
            } else {
                println(it)
                Toast.makeText(baseContext, "Ya existe una cuenta con este correo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun apiPostUser(){

        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)

        val user = User(0, binding.registerInputEmail.text.toString(),
                                    binding.registerInputPassword.text.toString(),
                                    binding.registerInputName.text.toString(),
                                    binding.registerInputLastname.text.toString(),
                                    binding.registerInputPhone.text.toString(),
                                    false, null)


        println(user)
        val resultUser: Call<User> = apiService.postUser(user)

        resultUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println(response.body())
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("Error: postUser() failure.")
            }
        } )

    }


}