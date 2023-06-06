package com.example.hw15

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hw15.data.PhotoFromMars
import com.example.hw15.databinding.SamplePhotoBinding

class MyAdapter(
    private val onClick: (PhotoFromMars) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<PhotoFromMars> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SamplePhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setData(data: List<PhotoFromMars>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            date.text = "Date: ${item?.earth_date}"
            cameraName.text = "Camera name: ${item?.camera?.name}"
            cameraFullName.text = "Full camera name: ${item?.camera?.full_name}"
            Pic.load(item?.img_src)
        }
        holder.binding.root.setOnClickListener {
            item?.let { onClick(item) }
        }
    }
}

class MyViewHolder(val binding: SamplePhotoBinding) : RecyclerView.ViewHolder(binding.root)