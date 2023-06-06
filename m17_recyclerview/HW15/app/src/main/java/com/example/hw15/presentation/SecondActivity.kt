package com.example.hw15.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.example.hw15.databinding.ActivitySecondBinding
import com.google.android.material.internal.ContextUtils.getActivity

class SecondActivity : AppCompatActivity() {
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var intent = intent
        binding.cameraName.text =  intent.getStringExtra("cameraName")
        binding.date.text=intent.getStringExtra("date")
        binding.cameraFullName.text = intent.getStringExtra("cameraFullName")
        binding.Pic.load(intent.getStringExtra("img"))
        binding.button.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            getActivity(this)?.finish()
            startActivity(intent)
        }
    }
}