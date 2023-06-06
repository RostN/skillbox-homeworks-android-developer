package com.example.hw8

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.hw8.databinding.ActivityMainBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val calendar = Calendar.getInstance()
    val lang = Locale.getDefault().toString()
    var dateFormat = SimpleDateFormat(
        when (lang) {
            "en_US" -> ("MM/dd/yyyy")
            else -> ("dd.MM.yyyy")
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Активация перехода из theme.xml
        val option = ActivityOptions.makeSceneTransitionAnimation(this)

        //Кнопка с ДР
        binding.birthday.setOnClickListener {
            val date = MaterialDatePicker.Builder.datePicker()
                .setTitleText(R.string.enterBirthDay)
                .build()
            date.addOnPositiveButtonClickListener { timeInMillis ->
                calendar.timeInMillis = timeInMillis
                val birthDay =
                    resources.getString(R.string.birthdayText) + " " + dateFormat.format(calendar.timeInMillis)
                binding.birthdayText.text = birthDay //ДР в текстовое поле
                Snackbar.make(binding.birthdayText, birthDay, Snackbar.LENGTH_SHORT)
                    .show() //Снек бар с ДР
                binding.birthday.isVisible = false //Выключение кнопки ДР
                binding.startButton.isVisible = true //Включаем кнопку начать
                binding.birthdayText.isVisible = true //Отображение ДР
            }
            date.show(supportFragmentManager, "DatePicker")
        }

        //Кнопка перехода на другое активити
        binding.startButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent, option.toBundle())
        }
    }
}