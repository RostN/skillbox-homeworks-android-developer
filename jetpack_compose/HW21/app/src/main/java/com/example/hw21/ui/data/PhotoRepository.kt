package com.example.hw21.ui.data

import com.example.hw21.ui.api.MovieCharacters
import com.example.hw21.ui.api.retrofit

class PhotoRepository {
    suspend fun getPhoto(page: Int): List<MovieCharacters> {
        return retrofit.loadList(page).results
    }
}