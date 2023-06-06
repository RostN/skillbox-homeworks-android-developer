package com.example.hw11.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hw11.R
import com.example.hw11.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    var inputRequest = ""
    var answer = ""

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Кнопка
        binding.searchButton.setOnClickListener {
            inputRequest = binding.searchLineEnter.text.toString()
            viewModel.startSearch(inputRequest)
            answer = resources.getString(R.string.resultLine)
        }

        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.state
                    .collect { state ->

                        when (state) {
                            State.Loading -> {
                                println("LOADING")
                                binding.progress.isVisible = true //Включение прогресса
                                binding.searchButton.isEnabled = false //Отключение кнопки
                                binding.searchLineEnter.text?.clear()
                            }
                            State.Answer -> {
                                println("ANSWER")
                                binding.progress.isVisible = false //Скрытие прогресса
                                binding.result.text = answer + inputRequest
                            }
                            is State.Waiting -> {
                                inputRequest = binding.searchLineEnter.text.toString()

                                println("COUNT: ${inputRequest.length}")
                                if (inputRequest.length < 3) {
                                    binding.searchButton.isEnabled = false //Отключение кнопки
                                    binding.progress.isVisible = false //Скрытие прогресса
                                    viewModel.startSearch(inputRequest)
                                } else {
                                    binding.searchButton.isEnabled = true
                                }
                            }
                            else -> {
                                inputRequest = binding.searchLineEnter.text.toString()
                            }
                        }
                    }
            }
    }
}

