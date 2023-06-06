package com.example.hw22

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.hw22.databinding.ActivityMainBinding
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val appComponent = (application as App).appComponent
        val koin = getKoin()
        binding.btnDagger.setOnClickListener {
            println("${appComponent.createBicycle()}")
        }
        binding.btnKoin.setOnClickListener {
            println("${koin}")
        }
    }
}