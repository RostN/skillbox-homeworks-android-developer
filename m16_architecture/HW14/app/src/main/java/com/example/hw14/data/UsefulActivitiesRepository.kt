package com.example.hw14.data

import com.example.hw14.entity.UsefulActivity
import javax.inject.Inject

class UsefulActivitiesRepository @Inject constructor(private val usefulActivityDataSource: UsefulActivityDataSource) {

    suspend fun getUsefulActivity(): UsefulActivity {
        return usefulActivityDataSource.action.getActionFromApi()
    }
}