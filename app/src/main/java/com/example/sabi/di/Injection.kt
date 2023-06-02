package com.example.sabi.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.sabi.data.api.ApiConfig
import com.example.sabi.local.LocalDataStore
import com.example.sabi.repository.Repository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("preferences")
object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(
            apiService,
            LocalDataStore.getInstance(context.dataStore),
        )
    }
}