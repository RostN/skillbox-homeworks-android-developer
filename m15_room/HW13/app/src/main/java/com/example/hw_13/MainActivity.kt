package com.example.hw_13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.hw_13.databinding.ActivityMainBinding
import com.example.hw_13.ui.main.App
import com.example.hw_13.ui.main.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val wordDao = (application as App).db.wordDao()
                return MainViewModel(wordDao) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textOut = binding.message
        val inputText = binding.enterText.text
        val WordDao = (application as App).db.wordDao()


        binding.addBtn.setOnClickListener {
            if (inputText != null) {

                if (inputText.isNotEmpty()) {
                    viewModel.onAddBtn(inputText.toString())
                } else Toast.makeText(this, "Пустая строка",Toast.LENGTH_SHORT).show()
            }
        }
        binding.clearBtn.setOnClickListener {
            viewModel.onDelBtn()
        }

        lifecycleScope.launchWhenCreated {
            viewModel.allWords
                .collect { words ->
                    var endSize = {
                        when (words.size) {
                            in 0..4 -> words.size
                            else -> 5
                        }
                    }
                    textOut.text = words.subList(0, endSize()).joinToString(separator = "\n")
                    inputText?.clear()
                }
        }
    }
}