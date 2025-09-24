package com.example.prepara_t

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostalCodeViewModel(application: Application) : AndroidViewModel(application) {

    fun savePostalCode(postalCode: String) {
        viewModelScope.launch {
            LocalStore.setPostalCode(getApplication(), postalCode)
        }
    }

    fun getPostalCode(callback: (String?) -> Unit) {
        viewModelScope.launch {
            val code = LocalStore.getPostalCode(getApplication())
            callback(code)
        }
    }
}

