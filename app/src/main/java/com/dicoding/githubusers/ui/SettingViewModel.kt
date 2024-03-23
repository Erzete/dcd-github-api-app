package com.dicoding.githubusers.ui

import androidx.lifecycle.ViewModel
import com.dicoding.githubusers.data.local.UserRepository

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getThemeSettings() = userRepository.getThemeSettings()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            userRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}