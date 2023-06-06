package com.example.hw14.presentation

import androidx.lifecycle.ViewModel
import com.example.hw14.domain.GetUsefulActivityUseCase
import com.example.hw14.entity.UsefulActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(var getUsefulActivityUseCase: GetUsefulActivityUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<State>(State.Answer)
    val state = _state.asStateFlow()
    suspend fun reloadUsefulActivity(): UsefulActivity {
        _state.value = State.Loading
        delay(1000)
        _state.value = State.Answer
        return getUsefulActivityUseCase.execute()
    }
}