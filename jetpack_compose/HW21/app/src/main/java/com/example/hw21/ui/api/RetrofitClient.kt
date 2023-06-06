package com.example.hw21.ui.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val URL = "https://rickandmortyapi.com"
val retrofit = Retrofit
    .Builder()
    .baseUrl(URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(Api::class.java)


interface Api {
    @GET("/api/character")
    suspend fun loadList(@Query("page") page: Int): Response

    @GET("/api/location")
    suspend fun loadListLocations(@Query("page") page: Int): LocationsResponse

    @GET("/api/episode/{number}")
    suspend fun loadEpisodes(@Path("number") number: Int): Episodes
}

//Episodes
data class Episodes(
    var name: String,
    val air_date: String,
    val episode: String
)

//Locations
data class LocationsResponse(
    val results: List<Locations>
)

data class Locations(
    val name: String,
    val type: String,
    val dimension: String,
)

//Characters
data class Response(
    val results: List<MovieCharacters>
)

data class MovieCharacters(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    val origin: Origin,
    val location: Location,
    val episode: List<Any>
)

data class Origin(
    val name: String
)

data class Location(
    val name: String
)