package com.example.sabi.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.sabi.data.api.ApiService
import com.example.sabi.data.local.LocalDataStore
import com.example.sabi.model.ResponseListItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class Repository(private val apiService: ApiService, private val localDataStore: LocalDataStore) {

    fun getState(): Flow<Boolean?> = localDataStore.getState()

    suspend fun saveState(state: Boolean) {
        localDataStore.saveState(state)
    }

    fun getDictonaryList(): LiveData<Result<List<ResponseListItem>>> = liveData(Dispatchers.IO) {
        emit(Result.Loading)
        try {
            val response = apiService.getDictonaryList()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
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