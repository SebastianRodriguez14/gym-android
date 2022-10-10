package com.tecfit.gym_android.activities.utilities

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.tecfit.gym_android.activities.LoginActivity
import com.tecfit.gym_android.activities.MainActivity

fun Context.login(name: String){
    val intent = Intent(this, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    startActivity(intent)
}

fun Context.logout(){
    FirebaseAuth.getInstance().signOut()

    val intent = Intent(this, LoginActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    startActivity(intent)
}
