package com.example.hw15.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hw15.MyAdapter
import com.example.hw15.data.PhotoFromMars
import com.example.hw15.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val photoAdapter = MyAdapter {PhotoFromMars -> onItemClick(PhotoFromMars)}
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

        binding.recyclerView.adapter = photoAdapter
        viewModel.photos.onEach {
            photoAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }
    fun onItemClick(item: PhotoFromMars) {
        val intent = Intent(activity, SecondActivity::class.java)
        intent.putExtra("date",item.earth_date)
        intent.putExtra("cameraFullName",item.camera.full_name)
        intent.putExtra("cameraName",item.camera.name)
        intent.putExtra("img",item.img_src)
        startActivity(intent)
    }
}