package com.example.hw23

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val retrofit = Retrofit
    .Builder()
    .baseUrl("https://api.sunrise-sunset.org")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Api::class.java)


interface Api {
    @GET("/json?")
    suspend fun loadList(@Query("lat") lat:Float, @Query("lng") lng:Float): Response
}


data class Response(
    val results: SunRise
)

data class SunRise(
    val sunrise: String
)