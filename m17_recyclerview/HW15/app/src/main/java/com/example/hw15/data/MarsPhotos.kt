package com.example.hw15.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarsPhotos(
    @Json(name = "photos") val photos: List<PhotoFromMars>
)

@JsonClass(generateAdapter = true)
data class PhotoFromMars(
    @Json(name = "id") val id: Int,
    @Json(name = "camera") val camera: Camera,
    @Json(name = "img_src") val img_src: String,
    @Json(name = "earth_date") val earth_date: String
)

@JsonClass(generateAdapter = true)
data class Camera(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "full_name") val full_name: String
)