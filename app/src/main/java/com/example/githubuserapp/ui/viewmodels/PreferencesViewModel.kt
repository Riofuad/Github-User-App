package com.example.githubuserapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubuserapp.data.local.SettingPreferences
import kotlinx.coroutines.launch

class PreferencesViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSetting(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(darkMode: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(darkMode)
        }
    }
}