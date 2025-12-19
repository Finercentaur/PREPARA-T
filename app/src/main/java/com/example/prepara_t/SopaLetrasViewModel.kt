package com.example.prepara_t

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SopaLetrasViewModel : ViewModel() {


    fun saveFoundWords(context: Context, postalCode: String, section: String, words: List<String>) {
        if (postalCode.isBlank()) return

        viewModelScope.launch {
            LocalStore.setFoundWords(
                context = context,
                postalCode = postalCode,
                section = section,
                words = words
            )
        }
    }

    fun saveAnswer(context: Context, postalCode: String, section: String, answer: String) {
        if (postalCode.isBlank()) return

        viewModelScope.launch {
            LocalStore.setAnswer(
                context = context,
                postalCode = postalCode,
                section = section,
                answer = answer
            )
        }
    }

    fun loadSopaState(
        context: Context,
        postalCode: String,
        section: String,
        onResult: (Set<String>, String?) -> Unit
    ) {
        if (postalCode.isBlank()) return

        viewModelScope.launch {
            val words = LocalStore.getFoundWords(context, postalCode, section)
            val answer = LocalStore.getAnswer(context, postalCode, section)
            onResult(words, answer)
        }
    }

}