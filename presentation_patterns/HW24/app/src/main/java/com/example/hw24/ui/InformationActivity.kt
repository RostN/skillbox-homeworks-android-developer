package com.example.hw24.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw24.databinding.ActivityInformationBinding

class InformationActivity : AppCompatActivity() {
    var dates =""
    var temps =""
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        //Загрузка данных из интента
        val mode = intent.getIntExtra("mode", 0)
        val tempNow = intent.getStringExtra("temps")?.split(", ")?.toList()
        val cityName = intent.getStringExtra("name")
        //Подключение Shared Preference БД
        sharedPreferences = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        //Внесение данных в зависимости от режима
        if (mode == 0) {
            dates = intent.getStringExtra("dates")?.replace(", ", "\n").toString()
            temps = intent.getStringExtra("temps")?.replace(", ", "\n").toString()
        }
        if (mode == 1) {
            sharedPreferences.getStringSet(cityName, emptySet())?.toList()?.forEach {
                temps = temps + "\n" + it.drop(19).replace(",", "")
                dates = dates + "\n" + it.subSequence(0, 16) as String
            }
        }
        //Подстановка всех данных в форму
        binding.dates.text = dates
        binding.temps.text = temps
        binding.cityName.text = cityName
        binding.tempNow.text = tempNow?.get(numberElement).toString()
    }
}