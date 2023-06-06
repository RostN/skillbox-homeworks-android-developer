package com.example.hw16.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.hw16.MyAdapter
import com.example.hw16.data.App
import com.example.hw16.data.Attractions
import com.example.hw16.databinding.ActivityFirstBinding

class FirstActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirstBinding
    private val photoAdapter = MyAdapter {Attractions -> onItemClick(Attractions)}
    val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val attractionsDao = (application as App).db.attractionsDao()
                return MainViewModel(attractionsDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Установка адаптера
        binding.recyclerView.adapter = photoAdapter
        //Добавление фото
        binding.addBtn.setOnClickListener {
            val intent = Intent(this, CameraMainActivity::class.java)
            startActivity(intent)
        }

        //Загрузка фото на старте
        lifecycleScope.launchWhenCreated {
            viewModel.allPhotos
                .collect {
                    photoAdapter.setData(viewModel.allPhotos.value)
                }
        }
    }
    fun onItemClick(item: Attractions) {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("date",item.date)
        intent.putExtra("img",item.uri)
        startActivity(intent)
    }
}