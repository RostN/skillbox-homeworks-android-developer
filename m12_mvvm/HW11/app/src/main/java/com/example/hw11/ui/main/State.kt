package com.example.hw11.ui.main

sealed class State {
    object Loading : State()
    object Answer : State()
    object Ready : State()
    data class Waiting (var inputRequest: String) : State()
}