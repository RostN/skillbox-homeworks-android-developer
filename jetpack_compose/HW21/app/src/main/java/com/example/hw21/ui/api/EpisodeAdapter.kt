package com.example.hw21.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw21.databinding.SampleEpisodesBinding
import com.example.hw21.ui.api.Episodes

var episodesCount = 1
class EpisodeAdapter : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<Episodes> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SampleEpisodesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setData(data: List<Episodes>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = episodesCount

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            name.text = item?.name
            episode.text = item?.episode
            date.text = item?.air_date
        }
    }
}

class MyViewHolder(val binding: SampleEpisodesBinding) : RecyclerView.ViewHolder(binding.root)