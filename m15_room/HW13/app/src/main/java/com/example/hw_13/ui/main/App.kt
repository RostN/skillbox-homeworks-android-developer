package com.example.hw_13.ui.main

import android.app.Application
import androidx.room.Room

class App : Application() {
    lateinit var db: AppDatabase
    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "db"
        ).build()
    }
}