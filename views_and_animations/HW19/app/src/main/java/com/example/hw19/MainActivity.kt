package com.example.hw19

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.hw19.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ResourceType")

    var seconds: Long = 0
    var hour = seconds / 3600
    var min = (seconds % 3600) / 60
    var sec = seconds % 60
    var rotationSec = 0F
    var pressedStart = false
    var time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec)
    var timer: CountDownTimer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val startButton = binding.startButton
        val resetButton = binding.resetButton



        startButton.setOnClickListener {
            pressedStart = !pressedStart
            if (pressedStart) {
                startButton.text = "Pause"
            } else {
                startButton.text = "Start"
            }
            runTimer()
        }
        resetButton.setOnClickListener {
            pressedStart = false
            startButton.text = "Start"
            seconds = 0
            update()
        }
    }

    private fun update() {
        val digitalTime = binding.timerDigital
        val secClock = findViewById<ImageView>(R.id.clock_sec)
        val minClock = findViewById<ImageView>(R.id.clock_min)
        val hourClock = findViewById<ImageView>(R.id.clock_hour)

        hour = seconds / 3600
        min = (seconds % 3600) / 60
        sec = seconds % 60
        secClock.rotation = (sec * 6).toFloat()
        minClock.rotation = (min * 6).toFloat()
        hourClock.rotation = (hour * 6).toFloat()
        time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, min, sec)
        digitalTime.text = time
    }

    private fun runTimer() {
        timer?.cancel()
        var end: Long = 86400000
        timer = object : CountDownTimer(end, 1000) {
            override fun onTick(p0: Long) {
                if (pressedStart) {
                    seconds++
                    end += 1000
                    if (seconds >= 86400) {
                        seconds = 0
                    }
                    update()
                }
            }

            override fun onFinish() {}
        }.start()
    }
}