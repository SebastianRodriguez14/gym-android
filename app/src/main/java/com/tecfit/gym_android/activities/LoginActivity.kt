package com.tecfit.gym_android.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForInternalStorageNotification
import com.tecfit.gym_android.activities.utilities.ForInternalStorageUser
import com.tecfit.gym_android.activities.utilities.ForNotifications
import com.tecfit.gym_android.activities.utilities.login
import com.tecfit.gym_android.databinding.ActivityLoginBinding
import com.tecfit.gym_android.models.Membership
import com.tecfit.gym_android.models.User
import com.tecfit.gym_android.models.custom.NotificationMembership
import com.tecfit.gym_android.models.custom.UserInAppCustom
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Member
import java.util.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val Google_SIGN_IN = 100
    private lateinit var binding: ActivityLoginBinding;
    private lateinit var context:Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        context = this
        googleLogIn()

        binding.loginEnter.setOnClickListener{
            val email = binding.loginInputEmail.text.toString()
            val password = binding.loginInputPassword.text.toString()

            if(email.isEmpty() || password.isEmpty()){
                binding.errorMessageEmail.visibility = if(email.isEmpty()) View.VISIBLE else View.INVISIBLE
                binding.errorMessagePassword.visibility = if(password.isEmpty()) View.VISIBLE else View.INVISIBLE
            }
            else {
                LogIn(email, password)
            }

        }

        binding.loginEnterRegister.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        binding.loginForgotPassword.setOnClickListener {
            startActivity(Intent(this, RecoverPasswordActivity::class.java))
        }

    }
    private fun LogIn(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")

                    if (auth.currentUser?.isEmailVerified != true){
                        Toast.makeText(this, "Correo electrónico no verificado.",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        fetchUser(email)
                        reload()
                    }

                } else {
                    println("Error en el login")
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Correo o contraseña incorrectos",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun reload(){
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    private fun googleLogIn(){
        binding.loginEnterGoogle.setOnClickListener{
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val googleClient = GoogleSignIn.getClient(this,googleConf)
            val signInIntent = googleClient.signInIntent
            startActivityForResult(signInIntent, Google_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == Google_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                if(account != null){
                    Log.d("Tag", "firebasegoogleid $account.id" )
                    firebaseAuthWithGoogle(account.idToken!!)
                }else{
                    Toast.makeText(this, "Este correo no existe.", Toast.LENGTH_LONG).show()
                }
            }
            catch (e:ApiException){
                Log.w("Tag", "Google sign in failed $e")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                Log.d("Tag", "signInWithCredential:success")
                val name = auth.currentUser?.displayName.toString()
                val email = auth.currentUser?.email.toString()
                val phone = auth.currentUser?.phoneNumber.toString()
                val photo = auth.currentUser?.photoUrl.toString()
                login(email)
            }else{
                Log.w("Tag", "signInWithCredential:failure", task.exception)
                Toast.makeText(this, "Email does not exist", Toast.LENGTH_LONG).show()
            }
        }
    }


    fun fetchUser(email: String){
        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)
        val resultUser: Call<User> = apiService.getUSer(email)

        resultUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                println("-----------------------------------------")
                println(response.body())
                UserInAppCustom.user = response.body()!!
                fetchMembershipByUser(UserInAppCustom.user!!.id_user)
                ForInternalStorageUser.saveUser(UserInAppCustom.user!!, context)
           }

            override fun onFailure(call: Call<User>, t: Throwable) {
                println("-----------------------------------------")
                println("Error: postUser() failure.")
                println("Er -> ${t.printStackTrace()}")
            }
        })
    }

    fun fetchMembershipByUser(id_user:Int){
        val apiService: ApiService = RetrofitClient.getRetrofit().create(ApiService::class.java)
        val resultMembership: Call<Membership> = apiService.getActiveMembershipByUser(id_user)

        resultMembership.enqueue(object : Callback<Membership> {
            override fun onResponse(call: Call<Membership>, response: Response<Membership>) {
                UserInAppCustom.membership = response.body()
                if (UserInAppCustom.membership != null) {
                    UserInAppCustom.membership!!.start_date = Date(UserInAppCustom.membership!!.start_date.time + (1000 * 60 * 60 * 24))
                    UserInAppCustom.membership!!.expiration_date = Date(UserInAppCustom.membership!!.expiration_date.time + (1000 * 60 * 60 * 24))
                    ForNotifications.checkSendNotification(context, this::class.java)
                } else {
                    UserInAppCustom.membership = Membership(0, Date(), Date(), 0.0)
                    ForInternalStorageNotification.disableNotification(context)
                }
            }

            override fun onFailure(call: Call<Membership>, t: Throwable) {
                println("Error: getActiveMembershipByUser() failure.")
            }
        })

    }


}