package com.example.hw21.ui.api

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw21.databinding.SamplePhotoBinding

class MyAdapter(
    private val onClick: (MovieCharacters) -> Unit
) :
    PagingDataAdapter<MovieCharacters, MyViewHolder>(DiffUtilCallback()) {
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
        holder.binding.root.setOnClickListener {
            item?.let { onClick(item) }
        }
    }
}
class DiffUtilCallback : DiffUtil.ItemCallback<MovieCharacters>() {
    override fun areItemsTheSame(oldItem: MovieCharacters, newItem: MovieCharacters): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: MovieCharacters, newItem: MovieCharacters): Boolean =
        oldItem == newItem
}
class MyViewHolder(val binding: SamplePhotoBinding) : RecyclerView.ViewHolder(binding.root)