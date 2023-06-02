package com.example.sabi.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.sabi.repository.Repository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: Repository) : ViewModel() {
    fun getState() = repository.getState().asLiveData(Dispatchers.IO)
}