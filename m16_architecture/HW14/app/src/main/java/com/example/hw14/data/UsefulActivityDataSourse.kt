package com.example.hw14.data

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

private const val BASE_URL = "https://www.boredapi.com"

class UsefulActivityDataSource @Inject constructor() {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val action: ActionApi = retrofit.create(ActionApi::class.java)

    interface ActionApi {
        @GET("/api/activity")
        suspend fun getActionFromApi(): UsefulActivityDto
    }
}