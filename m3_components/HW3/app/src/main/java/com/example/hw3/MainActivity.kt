package com.example.hw3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.example.hw3.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val seek = binding.seekBar
        val button = binding.button
        var clicks = 0 //Считалка кликов
        binding.textView.text = "0" //Стартовое значение таймера

        //Обновление данных в ползунке
        val updateAll = {
            binding.textView.text = seek.progress.toString()
            binding.progressBarCircle.progress = seek.progress
        }
        //Функции кнопки стоп
        val stopButton =
            {
                button.text = "Start"
                seek.progress = 0
                seek.isEnabled = true //Включение бегунка
                clicks-- //Уменьшение счетчика кликов
            }

        //Функции кнопки старт
        var startButton = {
            button.text = "Stop"
            seek.isEnabled = false //Выключение бегунка
            clicks++ //Увеличение счетчика кликов
            GlobalScope.launch {
                while (seek.progress > 0) {
                    seek.progress--
                    if (seek.progress == 0) {
                        yield()
                        runOnUiThread { stopButton() }
                    }
                    delay(1000)
                }
            }
        }

        //Кнопка
        button.setOnClickListener {
            when (clicks) {
                0 -> startButton()
                else -> stopButton()
            }
        }
        //Ползунок
        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar, progress: Int, fromUser: Boolean
            ) {
                // write custom code for progress is changed
                updateAll()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                updateAll()
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                updateAll()
            }
        })
    }
}