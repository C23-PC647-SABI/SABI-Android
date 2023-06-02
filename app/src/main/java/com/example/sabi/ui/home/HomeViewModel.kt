package com.example.sabi.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sabi.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {
    fun getState() = repository.getState().asLiveData(Dispatchers.IO)

    fun setState() {
        viewModelScope.launch {
            repository.saveState(true)
        }
    }
}