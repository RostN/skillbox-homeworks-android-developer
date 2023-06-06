package com.example.hw21.ui.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hw21.databinding.FragmentHomeBinding
import com.example.hw21.ui.api.MovieCharacters
import com.example.hw21.ui.api.MyAdapter
import com.example.hw21.ui.data.PhotoRepository
import com.example.hw21.ui.data.MainViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val photoAdapter = MyAdapter{MovieCharacters -> onItemClick(MovieCharacters)}
    private val repository = PhotoRepository()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
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

    private fun onItemClick(item: MovieCharacters) {
        val intent = Intent(activity, SecondActivity::class.java)
        intent.putExtra("name",item.name)
        intent.putExtra("lastLocation",item.location.name)
        intent.putExtra("liveStatus",item.status)
        intent.putExtra("episodes",item.episode.toString())
        intent.putExtra("img",item.image)
        intent.putExtra("species",item.species)
        startActivity(intent)
    }

    private fun startLoad() {
        binding.recyclerView.adapter = photoAdapter
        viewModel.pagedPhoto.onEach {
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}