package com.example.hw9

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import com.example.hw9.databinding.ActivityMainBinding

const val KEY_time = "time"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var timer: CountDownTimer? = null
    var state = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val seek = binding.seekBar
        val button = binding.button

        binding.textView.text = "0" //Стартовое значение таймера
        //Обновление данных в ползунке
        val updateAll = {
            binding.textView.text = seek.progress.toString()
            binding.progressBarCircle.progress = seek.progress
        }
        //Функции кнопки стоп
        val stopButton =
            {
                timer?.cancel()
                button.text = resources.getText(R.string.startButton)
                seek.progress = 0
                seek.isEnabled = true //Включение бегунка
            }

        //Функции кнопки старт
        val startButton = {
            timer?.cancel()
            seek.isEnabled = false
            timer = object : CountDownTimer((seek.progress.toLong() * 1000), 1000) {
                override fun onTick(p0: Long) {
                    button.text = resources.getText(R.string.stopButton)
                    seek.progress--
                    state = seek.progress
                    updateAll()
                }

                override fun onFinish() {
                    seek.progress = 0
                    state = seek.progress
                    updateAll()
                    seek.isEnabled = true
                }
            }.start()
        }

        //Кнопка
        button.setOnClickListener {
            when (state) {
                0 -> startButton()
                else -> stopButton()
            }
        }
        //Восстановление времени и изменение размеров в зависимости от ориентации
        savedInstanceState.let { bundle ->
            if (bundle != null) {
                state = bundle.getInt(KEY_time)
                seek.progress = state
                when (resources.configuration.orientation) {
                    Configuration.ORIENTATION_PORTRAIT -> {
                        binding.progressBarCircle.layoutParams.width = 900
                        binding.progressBarCircle.layoutParams.height = 900
                        binding.textView.textSize = 150F
                    }
                    else -> {
                        binding.progressBarCircle.layoutParams.width = 500
                        binding.progressBarCircle.layoutParams.height = 500
                        binding.textView.textSize = 100F
                    }
                }
                startButton()
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
    //Сохранение параметра времени
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        timer?.cancel()
        outState.putInt(KEY_time, state)

    }

}
