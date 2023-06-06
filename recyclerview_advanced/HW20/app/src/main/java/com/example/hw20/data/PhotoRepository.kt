package com.example.hw20.data

import com.example.hw20.api.*

class PhotoRepository {
    suspend fun getPhoto(page: Int): List<Character> {
        return retrofit.loadList(page).results
    }

}