package com.example.hw_13.ui.main

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Words::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao
}