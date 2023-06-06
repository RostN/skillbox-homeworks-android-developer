package com.example.hw16.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "attractions")
data class Attractions(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null, //ИД
    @ColumnInfo(name = "date") val date: String, //Дата
    @ColumnInfo(name = "uri") var uri: String // ссылка на фото
)