package com.example.hw21.ui.data

import androidx.lifecycle.ViewModel
import com.example.hw21.ui.api.Locations
import com.example.hw21.ui.api.retrofit

class DashboardViewModel : ViewModel() {

suspend fun load(page: Int): List<Locations> {
    return retrofit.loadListLocations(page).results
}
}
