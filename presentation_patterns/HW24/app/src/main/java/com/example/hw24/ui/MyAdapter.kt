package com.example.hw24.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw24.R
import com.example.hw24.databinding.SampleCityBinding

var numberElement = 0

class MyAdapter(
    private val onClick: (Cities) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<Cities> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SampleCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    //Установка данных
    fun setData(data: List<Cities>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    //Прокидывание данных в форму
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data.getOrNull(position)
        val dates = item?.dates?.split(", ")?.toList()
        val temperatureNow = item?.temps?.split(", ")?.toList()
        val timeNow = System.currentTimeMillis()
        var pic = 0
        var localDataPic = 0
        if (item?.dates != null) pic = R.drawable.temp_icon
        if (item?.id != null) localDataPic = R.drawable.storage_icon

        if (item?.dates != null) {
            for (i in 0..dates?.lastIndex!!) {
                if (timeNow < formatter.parse(dates[i]).time) {
                    numberElement = i - 1
                    exitTemperature = temperatureNow?.get(numberElement).toString()
                    break
                }
            }
        } else exitTemperature=""
        with(holder.binding) {
            cityName.text = item?.name
            latitude.text = item?.lat
            longitude.text = item?.lng
            temperature.text = exitTemperature
            temperature.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,pic,0)
            cityName.setCompoundDrawablesRelativeWithIntrinsicBounds(localDataPic,0,0,0)
        }
        holder.binding.root.setOnClickListener {
            item?.let { onClick(item) }
        }
    }
}

class MyViewHolder(val binding: SampleCityBinding) : RecyclerView.ViewHolder(binding.root)