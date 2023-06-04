package com.example.sabi.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.sabi.R
import com.example.sabi.databinding.ActivitySplashScreenBinding
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.home.HomeActivity
import com.example.sabi.ui.home.HomeViewModel
import com.example.sabi.ui.main.MainActivity

class SplashScreen : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val SplashViewModel: SplashViewModel by viewModels {
            factory
        }
        SplashViewModel.apply {
            getState().observe(this@SplashScreen) {
                if (it == null) {
                    val iv_logo = findViewById<ImageView>(R.id.iv_logo)

                    iv_logo.alpha = 0f
                    iv_logo.animate().setDuration(2000).alpha(1f).withEndAction {
                        toMain()
                    }
                }
                else{
                    val iv_logo = findViewById<ImageView>(R.id.iv_logo)

                    iv_logo.alpha = 0f
                    iv_logo.animate().setDuration(2000).alpha(1f).withEndAction {
                        toHome()
                    }
                }
            }

        }
    }
    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}