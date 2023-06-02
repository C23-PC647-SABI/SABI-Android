package com.example.sabi.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class LocalDataStore private constructor(private val dataStore: DataStore<Preferences>) {
    fun getState(): Flow<Boolean?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }

    suspend fun saveState(token: Boolean) {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    companion object {
        private val TOKEN_KEY = booleanPreferencesKey("token")
        @Volatile
        private var INSTANCE: LocalDataStore? = null
        fun getInstance(dataStore: androidx.datastore.core.DataStore<Preferences>): LocalDataStore {
            return INSTANCE ?: synchronized(this){
                val instance = LocalDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}