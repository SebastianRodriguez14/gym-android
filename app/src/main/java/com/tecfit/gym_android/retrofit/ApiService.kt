package com.tecfit.gym_android.retrofit

import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.Trainer
import com.tecfit.gym_android.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService  {

    @GET("product/all")
    fun getProducts():Call<List<Product>>

    @GET("trainer/all")
    fun getTrainers():Call<List<Trainer>>

    @POST("user/save")
    fun postUser(@Body user:User):Call<User>

}