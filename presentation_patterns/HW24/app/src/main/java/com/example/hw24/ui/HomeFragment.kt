package com.example.hw24.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.hw24.R
import com.example.hw24.databinding.FragmentHomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var exitTemperature = ""
var exitErrorConnect = ""
var exitTextEmptySearchResult = ""
const val sharedPrefFile = "kotlinsharedpreference"

class HomeFragment : Fragment() {
    val adapter = MyAdapter { Cities -> onItemClick(Cities) }
    var blockingRepeat = 0
    var newRequest = mutableListOf<String>()
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    lateinit var intent: Intent
    lateinit var oldRequest: Set<String>

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        exitTemperature = getString(R.string.loading_data)
        exitErrorConnect = getString(R.string.error_connect)
        exitTextEmptySearchResult = getString(R.string.exit_text_empty_result)
        sharedPreferences =
            requireActivity().getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        binding.recyclerView.adapter = adapter
        lifecycleScope.launch {
            modeServer(0)
            updateWindowInfo()
            delay(2000)
            updateWindowInfo()
        }
        return root
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Изначальный поиск
        liveSearch("")

        //Обработка событий в строке поиска
        binding.searchLine.addTextChangedListener {
            liveSearch(it?.toString()!!) //Мгноменный поиск по БД
            //Поиск по нажатию ENTER
            binding.searchLine.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    lifecycleScope.launch {
                        val loadingData: List<Cities>
                        binding.progress.isVisible = true
                        kotlin.runCatching {
                            modeServer(1)
                            retrofit.searchCity(it.toString()).results
                        }.fold(
                            onSuccess = {
                                val loadedRetrofitSearch = it
                                //загрузка городов из сети
                                loadingData = loadedRetrofitSearch.map { cityResult ->
                                    Cities(
                                        name = cityResult.name + " - " + cityResult.country,
                                        lat = cityResult.latitude.substring(
                                            0,
                                            when (cityResult.latitude.length) {
                                                in 0..5 -> cityResult.latitude.length
                                                else -> 5
                                            }
                                        ),
                                        lng = cityResult.longitude.substring(
                                            0,
                                            when (cityResult.latitude.length) {
                                                in 0..5 -> cityResult.latitude.length
                                                else -> 5
                                            }
                                        )
                                    )
                                }
                                //Прогрузка данных о погоде в найденных городах
                                loadingData.forEach {
                                    modeServer(0)
                                    val tempData = retrofit.loadList(it.lat, it.lng)
                                    it.temps = tempData.hourly.apparent_temperature.toString()
                                        .replace("[", "").replace("]", "")
                                    it.dates = tempData.hourly.time.toString()
                                        .replace("T", " ")
                                        .replace("[", "").replace("]", "")
                                }
                                binding.progress.isVisible = false
                                //Загрузка результатов поиска в адаптер
                                adapter.setData(loadingData)
                            },
                            onFailure = {
                                Toast.makeText(
                                    context,
                                    exitErrorConnect,
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    }
                    v.hideKeyboard() // Скрытие клавиатуры
                    return@OnKeyListener true
                } else false
            })
        }
        //Очистка текста перед вводом
        binding.searchLine.setOnClickListener {
            binding.searchLine.text.clear()
        }
    }

    //Поиск по БД
    fun liveSearch(request: String) {
        lifecycleScope.launch {
            val lookBase = viewModel.searchEnter(request)
            adapter.setData(lookBase)
            if (lookBase.isNotEmpty()) blockingRepeat = 0
            if (blockingRepeat == 0) {
                if (lookBase.isEmpty()) {
                    Toast.makeText(context, exitTextEmptySearchResult, Toast.LENGTH_LONG).show()
                    blockingRepeat = 1
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Автоматическое обновление данных
    fun updateWindowInfo() {
        viewModel.allCities.value.forEach {
            loadMode = 0
            viewModel.loadData(it.name, it.lat, it.lng, context)
        }
    }

    //Обработка нажатий по городу
    fun onItemClick(item: Cities) {
        //Прокидывание данных в интент
        intent = Intent(activity, InformationActivity::class.java)
        intent.putExtra("mode", 1)
        intent.putExtra("name", item.name)
        intent.putExtra("temps", item.temps)
        //Подготовка данных для SharingPreference БД
        val oldDate = sharedPreferences.getStringSet(item.name, emptySet())
        val tempNow = item.temps?.split(", ")?.toList()?.get(numberElement)
        val newDate = mutableListOf(formatter.format(System.currentTimeMillis()) + " - " + tempNow)
        //Получение прошлых запросов и объединение с новым запросом
        oldRequest = sharedPreferences.getStringSet("Request", emptySet()) as Set<String>
        newRequest = mutableListOf(item.name)
        //Сохранение данных в SharedPreference
        editor.putStringSet(item.name, oldDate?.union(newDate))
        editor.putStringSet("Request", oldRequest.union(newRequest.toSet()))
        editor.apply()
        //Добавление города и всех его данных в БД
        viewModel.onAddBtn(
            name = item.name,
            lat = item.lat,
            lng = item.lng,
            dates = item.dates,
            temps = item.temps
        )
        startActivity(intent)
    }
}