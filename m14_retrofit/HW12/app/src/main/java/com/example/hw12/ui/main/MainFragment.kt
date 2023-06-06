package com.example.hw12.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.hw12.databinding.FragmentMainBinding
import coil.load


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

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
        val textView = binding.messageFirst
        val textView2 = binding.message
        val image = binding.photo

        binding.button.setOnClickListener {
            viewModel.refresh()
        }

        lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        textView2.text = ""
                        textView.text = ""
                        image.isVisible = false
                        binding.progress.isVisible = true
                        binding.button.isEnabled = false
                    }
                    State.Answer -> {
                        binding.progress.isVisible = false
                        image.isVisible = true
                        binding.button.isEnabled = true
                        val response = RetrofitInstance.personInfo.getInfo().results.get(0)

                        val fullName =
                            response.name.title + ". " + response.name.first + " " + response.name.last //ФИО
                        val gender = response.gender // Пол
                        val dOB =
                            response.dob.date?.substringBefore("T") + " (Age: " + response.dob.age + ")" //ДР
                        val naz = response.nat //Национальность
                        val telMob = response.phone //Мобильный
                        val telSt = response.cell //Городской, или просто похож)
                        //Адрес
                        val address =
                            response.location.country + " " + response.location.postcode + " " + response.location.state + " " + response.location.city + " St. " + response.location.street?.name + " " + response.location.street?.number
                        val coordinates =
                            response.location.coordinates.latitude + " " + response.location.coordinates.longitude //Координаты
                        val timezone =
                            response.location.timezone.offset + " " + response.location.timezone.description //Время и название зоны
                        val email = response.email //Почта
                        val login = response.login.username //Логин
                        var password = response.login.password //Пароль
                        val ID = response.id.name + " " + response.id.value //ИД карточки
                        val regDate =
                            response.registered.date?.substringBefore("T") + " (Age: " + response.registered.age + ")" //Дата регистрации и срок
                        val picHuge = response.picture.large //Большая фотка

                        textView.text =
                            fullName + "\n" + gender + "\n" + dOB + "\n" + naz + "\n" + ID
                        textView2.text =
                            "Address:\n" + address + "\n\nCoordinates\n" + coordinates + "\n\nTimezone:\n" + timezone + "\n\nContacts:\n" + telMob + "\n" + telSt + "\n" + email + "\n\nRegistration date:\n" + regDate + "\n\nLogin:\n" + login + "\n\nPassword:\n" + password
                        image.load(picHuge)
                    }
                }
            }
        }
    }
}