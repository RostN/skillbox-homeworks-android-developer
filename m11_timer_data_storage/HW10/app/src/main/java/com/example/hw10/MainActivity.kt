package com.example.hw10

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw10.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var prefs: SharedPreferences
    val repository = Repository() //Репозиторий

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE)

        //Загрузка текста на старте
        binding.importText.text = repository.getText(this)

        //Кнопка сохранить
        binding.saveButton.setOnClickListener {
            val savedText = binding.enterText.text.toString()
            repository.saveText(savedText)
//            prefs.edit().putString(SHARED_PREF_KEY, savedText).apply()
            binding.importText.text = prefs.getString(SHARED_PREF_KEY, "EMPTY")
        }

        //Кнопка очистки
        binding.clearButton.setOnClickListener {
            repository.clearText()
            binding.enterText.text.clear()
        }

        //поле EditText, очистка поля, как только тынули в него
        binding.enterText.setOnClickListener {
            binding.enterText.text.clear()
        }
    }
}