package com.example.hw14.domain

import com.example.hw14.data.UsefulActivitiesRepository
import com.example.hw14.entity.UsefulActivity
import javax.inject.Inject

class GetUsefulActivityUseCase @Inject constructor(
    private val usefulActivitiesRepository: UsefulActivitiesRepository
) {
    suspend fun execute(): UsefulActivity {
        return usefulActivitiesRepository.getUsefulActivity()
    }
}