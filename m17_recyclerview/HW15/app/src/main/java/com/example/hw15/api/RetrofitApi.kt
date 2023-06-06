package com.example.hw15.api

import com.example.hw15.data.MarsPhotos
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://api.nasa.gov"

class RetrofitApi {
}

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
val photo: PhotoApi = retrofit.create(PhotoApi::class.java)

interface PhotoApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=${api_key}")
    suspend fun getPhotoFromApi(): MarsPhotos


    private companion object {
        private const val api_key = "YOUR_API_KEI" //DEMO KEY with limit
    }
}