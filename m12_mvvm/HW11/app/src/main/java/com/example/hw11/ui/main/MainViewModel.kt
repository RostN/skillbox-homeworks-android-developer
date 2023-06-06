package com.example.hw11.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Waiting(inputRequest = ""))
    val state = _state.asStateFlow()


    fun startSearch(inputRequest: String) {
        viewModelScope.launch {
            if (inputRequest.length < 3) {
                delay(100)
                _state.value = State.Ready
                delay(100)
                _state.value = State.Waiting(inputRequest)
            } else {
                _state.value = State.Loading
                delay(5000)
                _state.value = State.Answer
                delay(100)
                _state.value = State.Waiting(inputRequest)
            }
        }
    }

}