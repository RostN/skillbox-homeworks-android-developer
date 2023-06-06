package com.example.hw21.ui.data

import androidx.lifecycle.ViewModel
import com.example.hw21.ui.api.Episodes
import com.example.hw21.ui.api.retrofit

class NotificationsViewModel : ViewModel() {
    suspend fun load(number: Int): Episodes {
        return retrofit.loadEpisodes(number)
    }
}