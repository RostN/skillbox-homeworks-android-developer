package com.example.hw20.api

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw20.databinding.SamplePhotoBinding

class MyAdapter :
    PagingDataAdapter<com.example.hw20.api.Character, MyViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SamplePhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.binding) {
            name.text = item?.name
            origin.text = item?.origin?.name
            lastLocation.text = item?.location?.name
            live.text = item?.status
            if (item?.status == "Alive") statusOfLive.setColorFilter(Color.GREEN)
            if (item?.status == "unknown") statusOfLive.setColorFilter(Color.LTGRAY)
            if (item?.status == "Dead") statusOfLive.setColorFilter(Color.RED)
            Pic.load(item?.image)
        }
    }
}
class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem
}
class MyViewHolder(val binding: SamplePhotoBinding) : RecyclerView.ViewHolder(binding.root)