package com.example.sabi.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sabi.data.di.Injection
import com.example.sabi.data.repository.Repository
import com.example.sabi.ui.home.HomeViewModel
import com.example.sabi.ui.splash.SplashViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory private constructor(private val repository: Repository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> return HomeViewModel(repository) as T
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> return SplashViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel : " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }.also { instance = it }
    }
}