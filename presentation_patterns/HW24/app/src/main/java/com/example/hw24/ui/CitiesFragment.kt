package com.example.hw24.ui

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.hw24.R
import com.example.hw24.databinding.FragmentDashboardBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm")

class DashboardFragment : Fragment() {
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    var lat = ""
    var lng = ""
    var cityName = ""
    val adapter = MyAdapter { Cities -> onItemClick(Cities) }

    val viewModel: com.example.hw24.ui.ViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val citiesDao = (activity?.application as App).db.CitiesDao()
                return ViewModel(citiesDao) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        context?.deleteDatabase("db") //Удаление базы данный целиком
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Установка адаптера
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            updateWindowInfo()
            delay(1000)
            updateWindowInfo()
        }

        //Отображения окна для нового города
        binding.openAddCity.setOnClickListener {
            binding.addCityMainWindow.isVisible = true
        }

        //Ввод нового города в БД
        binding.addCityBtn.setOnClickListener {
            lat = binding.enterLatitude.text.toString()
            lng = binding.enterLongitude.text.toString()
            cityName = binding.enterNameCity.text.toString()
            val exitCityText = "${getString(R.string.code_text_toast)} $cityName"
            val exitLatText = "${getString(R.string.code_text_lat)} $lat"
            val exitLngText = "${getString(R.string.code_text_lng)} $lng"
            val exitText = "$exitCityText\n$exitLatText\n$exitLngText"
            //Внесение города в БД
            viewModel.onAddBtn(
                name = cityName,
                lat = lat,
                lng = lng,
                lastUpdates = formatter.format(System.currentTimeMillis())
            )
            //Очистка полей
            binding.enterLongitude.text.clear()
            binding.enterLatitude.text.clear()
            binding.enterNameCity.text.clear()

            //Закрытие окна
            binding.addCityMainWindow.isVisible = false
            Toast.makeText(context, exitText, Toast.LENGTH_LONG).show()
        }

        //Закрытие окна если тыкнуть вне поля
        binding.addCityBackground.setOnClickListener {
            binding.addCityMainWindow.isVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Обработка нажатий на элемент
    fun onItemClick(item: Cities) {
        val intent = Intent(activity, InformationActivity::class.java)
        intent.putExtra("name", item.name)
        intent.putExtra("dates", item.dates)
        intent.putExtra("temps", item.temps)
        intent.putExtra("mode", 0)
        startActivity(intent)
    }

    //Автоматическое обновление данных
    suspend fun updateWindowInfo() {
        lifecycleScope.launchWhenCreated {
            viewModel.allCities
                .collect {
                    adapter.setData(viewModel.allCities.value)
                }
        }
        viewModel.allCities.value.forEach {
            modeServer(0)  // Изменение режима загрузки данных по координатам
            viewModel.loadData(it.name, it.lat, it.lng, context)
        }
    }
}