package com.example.hw12.ui.main

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://randomuser.me"
object RetrofitInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val personInfo: PersonInfo = retrofit.create(PersonInfo::class.java)
}

interface PersonInfo {
    @GET("api")
    suspend fun getInfo(): PersonalInfo
}