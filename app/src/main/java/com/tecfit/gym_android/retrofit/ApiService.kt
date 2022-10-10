package com.tecfit.gym_android.retrofit

import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.tecfit.gym_android.models.Product
import com.tecfit.gym_android.models.Trainer
import com.tecfit.gym_android.models.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService  {

    @GET("product/all")
    fun getProducts():Call<List<Product>>

    @GET("trainer/all")
    fun getTrainers():Call<List<Trainer>>

    //USER
    @GET("user/search/{email}")
    fun getUSer(@Path("email") email:String): Call<User>

    @POST("user/save")
    fun postUser(@Body user:User):Call<User>

}