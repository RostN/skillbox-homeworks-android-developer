package com.example.hw7

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw7.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val option = ActivityOptions.makeSceneTransitionAnimation(this) //Активация перехода

        //Кнопка перехода на другое активити
        binding.startButton.setOnClickListener {
            var intent = Intent(this, QuizActivity::class.java)
            startActivity(intent, option.toBundle())
        }
    }
}