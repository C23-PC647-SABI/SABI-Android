package com.example.sabi.ui.dictonary

import androidx.lifecycle.ViewModel

import com.example.sabi.data.repository.Repository

class DictonaryViewModel(private val repository: Repository) : ViewModel() {
    fun getList() = repository.getDictonaryList()
}