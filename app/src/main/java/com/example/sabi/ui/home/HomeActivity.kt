package com.example.sabi.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.sabi.R
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.main.MainViewModel

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val HomeViewModel: HomeViewModel by viewModels {
            factory
        }
        HomeViewModel.apply {
            getState().observe(this@HomeActivity) {
                if (it == null) {
                    setState()
                }
            }

        }

    }
}