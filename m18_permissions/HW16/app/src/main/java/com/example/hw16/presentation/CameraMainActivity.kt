package com.example.hw16.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hw16.R
import com.example.hw16.databinding.ActivityMainBinding

class CameraMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, CameraFragment.newInstance())
                .commitNow()
        }
    }
}