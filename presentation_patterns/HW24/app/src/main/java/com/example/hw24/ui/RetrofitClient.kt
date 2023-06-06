package com.example.hw24.ui

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

var loadMode = 0
var baseUrl = "https://api.open-meteo.com"
lateinit var retrofit: Api
//Функция с динамичным адресом для ретрофита
fun modeServer(loadMode: Int = 0) {
    baseUrl = when (loadMode) {
        0 -> "https://api.open-meteo.com"
        else -> "https://geocoding-api.open-meteo.com"
    }
    retrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Api::class.java)
}

interface Api {
    //Загрузка температуры по координатам
    @GET("/v1/forecast?")
    suspend fun loadList(
        @Query("latitude") lat: String,
        @Query("longitude") lng: String,
        @Query("hourly") hourly: String = "apparent_temperature",
        @Query("forecast_days") days: Int = 2
    ): Response

    //Поиск города по названию
    @GET("/v1/search?")
    suspend fun searchCity(
        @Query("name") name: String,
        @Query("count") count: Int = 10,
        @Query("language") language: String = "ru",
        @Query("format") format: String = "json"
    ): SearchResult
}

//Данные по городам
data class SearchResult(
    var results: List<CityResult>
)

data class CityResult(
    val name: String,
    val latitude: String,
    val longitude: String,
    val country: String
)

//Данные по температуре
data class Response(
    val hourly: Time
)

data class Time(
    val time: List<String>,
    val apparent_temperature: List<Float>
)

