package com.example.hw15.data

import com.example.hw15.api.photo

class PhotoRepository
{
    suspend fun getPhoto():List<PhotoFromMars>{
        return photo.getPhotoFromApi().photos
    }
}