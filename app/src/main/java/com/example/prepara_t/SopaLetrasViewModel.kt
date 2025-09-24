package com.example.prepara_t

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SopaLetrasViewModel : ViewModel() {
    fun saveFoundWords(context: Context, section: String, words: List<String>) {
        viewModelScope.launch {
            LocalStore.setFoundWords(context, section, words)
        }
    }

    fun saveAnswer(context: Context, section: String, answer: String) {
        viewModelScope.launch {
            LocalStore.setAnswer(context, section, answer)
        }
    }
}