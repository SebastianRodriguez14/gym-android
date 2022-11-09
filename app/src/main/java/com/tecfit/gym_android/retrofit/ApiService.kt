package com.tecfit.gym_android.retrofit

import com.tecfit.gym_android.models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("product/all")
    fun getProducts(): Call<List<Product>>

    @GET("trainer/all")
    fun getTrainers(): Call<List<Trainer>>

    //USER
    @GET("user/search/{email}")
    fun getUSer(@Path("email") email: String): Call<User>

    @POST("user/save")
    fun postUser(@Body user: User): Call<User>

    @GET("membership/check/{id_user}")
    fun getActiveMembershipByUser(@Path("id_user") id_user: Int): Call<Membership>

    @GET("routine/search/{id_body_part}")
    fun getRoutinesForBodyPart(@Path("id_body_part") id_body_part:Int): Call<List<Routine>>

    @GET("routine/exercises/{id_routine}")
    fun getExercisesByRoutine(@Path("id_routine") id_routine:Int): Call<List<Exercise>>

    @GET("routine/list_random")
    fun getRandomRoutines():Call<List<Routine>>

}