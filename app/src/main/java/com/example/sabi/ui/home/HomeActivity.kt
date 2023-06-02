package com.example.sabi.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.example.sabi.R
import com.example.sabi.databinding.ActivityHomeBinding
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.dictonary.DictonaryActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        binding.apply {
            btnCamera.setOnClickListener{

            }
            btnDictonary.setOnClickListener{
                val intent = Intent(this@HomeActivity, DictonaryActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}