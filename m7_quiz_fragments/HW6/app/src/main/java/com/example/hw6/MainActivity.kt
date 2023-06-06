package com.example.hw6

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw6.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        //Кнопка перехода на другое активити
        binding.startButton.setOnClickListener {

            var intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }

        setContentView(binding.root)
    }
}