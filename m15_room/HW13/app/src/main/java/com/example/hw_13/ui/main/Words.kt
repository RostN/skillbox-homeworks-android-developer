package com.example.hw_13.ui.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Words(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int? = null, //ИД
    @ColumnInfo(name = "word") val word: String, //Слова
    @ColumnInfo(name = "count") var count: Int? = 1 // Количество повторений
)
