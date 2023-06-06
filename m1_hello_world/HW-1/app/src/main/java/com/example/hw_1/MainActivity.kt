package com.example.hw_1

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.hw_1.databinding.ActivityMainBinding
import java.time.format.TextStyle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var counter = 0
        var counterLimit = 50
        if (counter == 0) binding.minusButton.isEnabled = false

        //Кнопка плюс
        binding.plusButton.setOnClickListener {
            if (counter < counterLimit) {
                binding.titleText.setTextColor(Color.BLUE)
                counter++
                binding.titleText.text = "Осталось мест: " + (counterLimit - counter).toString()
                binding.minusButton.isEnabled = true
            }
            if (counter == counterLimit) {
                binding.plusButton.isEnabled = false
                binding.resetButton.visibility = View.VISIBLE
                binding.titleText.setTextColor(Color.RED)
            }
        }

        //кнопка минус
        binding.minusButton.setOnClickListener {
            if (counter > 0) {
                counter--
                binding.titleText.text = "Осталось мест: " + (counterLimit - counter).toString()

                if (counter < counterLimit) {
                    binding.plusButton.isEnabled = true
                    binding.titleText.setTextColor(Color.BLUE)
                    binding.resetButton.visibility = View.INVISIBLE
                }
                if (counter == 0) {
                    binding.titleText.text = "Все места свободны"
                    binding.titleText.setTextColor(Color.GREEN)
                    binding.minusButton.isEnabled = false
                }
            }
        }
        //Кнопка сброс
        binding.resetButton.setOnClickListener {
            counter = 0
            binding.titleText.setTextColor(Color.GREEN)
            binding.minusButton.isEnabled = false
            binding.plusButton.isEnabled = true
            binding.titleText.text = "Все места свободны"
            binding.resetButton.visibility = View.INVISIBLE
        }

    }
}