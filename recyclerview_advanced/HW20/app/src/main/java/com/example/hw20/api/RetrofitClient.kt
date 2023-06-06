package com.example.hw20.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

val retrofit = Retrofit
    .Builder()
    .baseUrl("https://rickandmortyapi.com")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Api::class.java)


interface Api {
    @GET("/api/character")
    suspend fun loadList(@Query("page") page:Int): Response
}


data class Response(
    val results: List<Character>
)

data class Character(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location
)

data class Origin(
    val name: String
)

data class Location(
    val name: String
)