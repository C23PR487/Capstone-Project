package io.github.c23pr487.lapakin.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PreferenceViewModel : ViewModel() {
    private val _selectedCity = MutableLiveData<String?>()
    val selectedCity: LiveData<String?> = _selectedCity

    fun changeCity(label: String?) {
        _selectedCity.value = label
    }
}