package com.example.hw16.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttractionsDao {
    @Query("SELECT*FROM attractions")
    fun getAll(): Flow<List<Attractions>>

    @Insert
    suspend fun insert(data: Attractions)

    @Delete
    suspend fun delete(data: List<Attractions>)

    @Update
    suspend fun update(data: Attractions)
}