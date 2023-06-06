package com.example.hw24.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ViewModel(private val citiesDao: CitiesDao) : ViewModel() {

    //Функция добавление в БД
    fun onAddBtn(
        name: String,
        lat: String,
        lng: String,
        dates: String? = null,
        temps: String? = null,
        lastUpdates: String? = null
    ) {
        viewModelScope.launch {
            citiesDao.insert(
                Cities(
                    name = name,
                    lat = lat,
                    lng = lng,
                    dates = dates,
                    temps = temps,
                    lastUpdates = lastUpdates
                )
            )
        }
    }

    //Функция обновление БД
    fun updateInfo(
        name: String,
        dates: String? = null,
        temps: String? = null,
        lastUpdates: String? = null
    ) {
        viewModelScope.launch {
            val searchCity = citiesDao.searchWords(enterText = name)
            if (searchCity.isNotEmpty()) {
                searchCity.last().let {
                    val update = it.copy(
                        dates = dates,
                        temps = temps,
                        lastUpdates = lastUpdates
                    )
                    citiesDao.update(update)
                }
            }
        }
    }

    //Функция закгрузки данных из сети и обновление БД
    fun loadData(name: String, lat: String, lng: String, context: Context?) {
        viewModelScope.launch {
            kotlin.runCatching {
                retrofit.loadList(lat, lng)
            }.fold(
                onSuccess = {
                    val dates = it.hourly.time.toString().replace("T", " ")
                        .replace("[", "").replace("]", "")
                    val temps = it.hourly.apparent_temperature.toString()
                        .replace("[", "").replace("]", "")
                    val lastUpdates = formatter.format(System.currentTimeMillis())
                    updateInfo(name, dates, temps, lastUpdates)
                },
                onFailure = {
                    Toast.makeText(context, exitErrorConnect, Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    val allCities = this.citiesDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
    //Поиск по БД введенного слова
    suspend fun searchEnter(enterText:String): List<Cities> {
        return citiesDao.searchWordsPart(enterText)
    }
}