package com.example.hw_13.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {
    val allWords = this.wordDao.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun onAddBtn(enterText: String) {
        viewModelScope.launch {
            val sw = wordDao.searchWords(enterText)
            //Проверка на повтор слова в базе
            if (sw.isNotEmpty()){
                sw.last().let {
                    val countWord = sw.last().count
                    val update = it.copy(count = countWord?.plus(1))
                    wordDao.update(update)}
            }
            else wordDao.insert(Words(word = enterText))
            }
        }

    fun onDelBtn() {
        viewModelScope.launch {
            wordDao.delete(allWords.value) //Очистка базы
        }
    }

}