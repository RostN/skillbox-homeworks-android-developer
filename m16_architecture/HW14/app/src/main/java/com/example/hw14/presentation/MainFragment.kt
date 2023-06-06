package com.example.hw14.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hw14.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {
    companion object {
        fun newInstance() = MainFragment()
    }

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
        val textView = binding.messageFirst

        binding.button.setOnClickListener {
            lifecycleScope.launch {
                viewModel.reloadUsefulActivity()
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        textView.text = ""
                        binding.progress.isVisible = true
                        binding.button.isEnabled = false
                    }
                    State.Answer -> {
                        binding.progress.isVisible = false
                        binding.button.isEnabled = true
                        val response = viewModel.getUsefulActivityUseCase.execute()
                        val act = response.activity
                        val url = response.link
                        textView.text = act + "\n\n" + url
                    }
                }
            }
        }
    }
}