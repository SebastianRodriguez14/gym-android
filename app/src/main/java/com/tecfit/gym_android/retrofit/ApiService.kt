package com.tecfit.gym_android.retrofit

import com.tecfit.gym_android.models.*
import com.tecfit.gym_android.models.custom.UserCustom
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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

    @Multipart
    @PUT("file/put")
    fun updateFile(@Part multipartFile: MultipartBody.Part, @Part("idFile") idFile: RequestBody):Call<File>

    @PUT("user/update/{email}")
    fun putUser(@Body user:UserCustom, @Path("email") email:String):Call<User>

}