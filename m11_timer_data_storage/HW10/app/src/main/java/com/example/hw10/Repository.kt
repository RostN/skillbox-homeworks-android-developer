package com.example.hw10

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

const val PREF_NAME = "preference name"
const val SHARED_PREF_KEY = "shared preference key"

class Repository {
    var localValue: String? = "Local value"
    lateinit var prefs: SharedPreferences

    fun getText(context: Context): String {
        return when {
            getDataFromSharedPreference(context) != null -> getDataFromSharedPreference(context)!!
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            else -> "No data"
        }
    }


    //Взятие данных из SharedPreference
    private fun getDataFromSharedPreference(context: Context): String? {
        prefs = context.getSharedPreferences(PREF_NAME, MODE_PRIVATE)
        return prefs.getString(SHARED_PREF_KEY, "EMPTY")
    }

    //Взятие данынх из локальной переменной
    private fun getDataFromLocalVariable(): String? {

        return localValue
    }

    //Сохранение данных в локальную переменную и в SharedPreference
    fun saveText(text: String) {
        prefs.edit().putString(SHARED_PREF_KEY,text).apply()
        localValue = text
    }

    //Очистка локальной переменной и SharedPreference
    fun clearText() {
        localValue = ""

    }
}