package com.example.hw20.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.hw20.api.Api
import com.example.hw20.api.MyAdapter
import com.example.hw20.api.retrofit
import com.example.hw20.data.PhotoPagingSource
import com.example.hw20.data.PhotoRepository
import com.example.hw20.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val photoAdapter = MyAdapter()
    private lateinit var binding: FragmentMainBinding
    private val repository = PhotoRepository()
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
        binding.reloadButton.isVisible = false
        binding.reloadButton.setOnClickListener {
            binding.reloadButton.isVisible = false
            firstLoad()
        }
        firstLoad()
    }

    private fun firstLoad() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                if (repository.getPhoto(1).isNotEmpty()) startLoad()
            } catch (e: Exception) {
                binding.reloadButton.isVisible = true
            }
        }
    }

    private fun startLoad() {
        binding.recyclerView.adapter = photoAdapter
        viewModel.pagedPhoto.onEach {
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}