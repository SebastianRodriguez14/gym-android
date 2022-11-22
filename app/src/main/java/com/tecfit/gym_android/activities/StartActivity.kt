package com.tecfit.gym_android.activities

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import com.tecfit.gym_android.R
import com.tecfit.gym_android.activities.utilities.ForInternalStorageNotification
import com.tecfit.gym_android.activities.utilities.ForInternalStorageUser
import com.tecfit.gym_android.activities.utilities.ForNotifications
import com.tecfit.gym_android.models.Membership
import com.tecfit.gym_android.models.custom.UserInAppCustom
import com.tecfit.gym_android.retrofit.ApiService
import com.tecfit.gym_android.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class StartActivity : AppCompatActivity() {

    private lateinit var text_start:TextView
    val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var context:Context


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        context = this
        text_start = findViewById(R.id.text_start)
        text_start.setOnClickListener {
            checkUserLogin()
        }
    }



    fun checkUserLogin(){
        UserInAppCustom.user = ForInternalStorageUser.loadUser(this)
        if (UserInAppCustom.user == null) {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        } else {
            fetchMembershipByUser(UserInAppCustom.user!!.id_user)
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
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
                    ForNotifications.sendNotification(context)
                } else {
                    UserInAppCustom.membership = Membership(0, Date(), Date(), 0.0)
                    ForInternalStorageNotification.disableNotification(context)

                }
            }

            override fun onFailure(call: Call<Membership>, t: Throwable) {
                println("Error: getActiveMembershipByUser() failure.")
                fetchMembershipByUser(id_user)
            }
        })

    }








}