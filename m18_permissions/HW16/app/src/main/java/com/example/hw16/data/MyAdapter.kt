package com.example.hw16

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw16.data.Attractions
import com.example.hw16.databinding.SamplePhotoBinding

class MyAdapter(
    private val onClick: (Attractions) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<Attractions> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SamplePhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setData(data: List<Attractions>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            date.text = item?.date
            Pic.load(item?.uri)
        }
        holder.binding.root.setOnClickListener {
            item?.let { onClick(item) }
        }
    }
}

class MyViewHolder(val binding: SamplePhotoBinding) : RecyclerView.ViewHolder(binding.root)