package com.example.hw14.data

import com.example.hw14.entity.UsefulActivity
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UsefulActivityDto(
     @Json(name = "activity") override var activity: String,
     @Json(name = "link") override var link: String
) : UsefulActivity