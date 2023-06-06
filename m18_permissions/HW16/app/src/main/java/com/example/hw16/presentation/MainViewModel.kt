package com.example.hw16.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hw16.data.Attractions
import com.example.hw16.data.AttractionsDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val attractionsDao: AttractionsDao) : ViewModel() {
    val allPhotos = this.attractionsDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun onAddBtn(date: String, uri: String) {
        viewModelScope.launch {
            attractionsDao.insert(Attractions(date = date, uri = uri))
        }
    }

    fun onDelBtn() {
        viewModelScope.launch {
            attractionsDao.delete(allPhotos.value) //Очистка базы
        }
    }

}