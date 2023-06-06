package com.example.hw_13.ui.main

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT*FROM words")
    fun getAll(): Flow<List<Words>>

    @Insert
    suspend fun insert(word: Words)

    @Update
    suspend fun update(word: Words)

    @Delete
    suspend fun delete(word: List<Words>)

    @Query("SELECT*FROM words WHERE word LIKE :enterText")
    suspend fun searchWords(enterText: String): List<Words>

}