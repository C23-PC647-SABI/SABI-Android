package com.example.sabi.data.repository

import com.example.sabi.data.api.ApiService
import com.example.sabi.data.local.LocalDataStore
import kotlinx.coroutines.flow.Flow

class Repository(apiService: ApiService, private val localDataStore: LocalDataStore) {

    fun getState(): Flow<Boolean?> = localDataStore.getState()

    suspend fun saveState(state: Boolean) {
        localDataStore.saveState(state)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            prefDataStore: LocalDataStore,
        ): Repository = instance ?: synchronized(this) {
            instance ?: Repository(apiService, prefDataStore)
        }.also { instance = it }
    }

}