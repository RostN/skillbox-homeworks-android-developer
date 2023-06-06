package com.example.hw2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hw2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.CV.setTextFirstLine("Верхняя строчка, настроенная из кода")
        binding.CV.setTextSecondLine("Нижняя строчка, настроенная из кода")
    }
}