package com.example.sabi.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.sabi.data.repository.Repository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(private val repository: Repository) : ViewModel() {
    fun getState() = repository.getState().asLiveData(Dispatchers.IO)
}