package com.example.sabi.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.sabi.R
import com.example.sabi.databinding.ActivityMainBinding
import com.example.sabi.ui.home.HomeActivity
import com.example.sabi.ui.onboarding.screen.OnboardingFirstFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val first_onboarding_fragment = OnboardingFirstFragment()
        val fragment = fragmentManager.findFragmentByTag(OnboardingFirstFragment::class.java.simpleName)

        if (fragment !is OnboardingFirstFragment) {
            Log.d(
                "MyFlexibleFragment",
                "Fragment Name :" + OnboardingFirstFragment::class.java.simpleName
            )
            fragmentManager
                .beginTransaction()
                .add(
                    R.id.frame_container,
                    first_onboarding_fragment,
                    OnboardingFirstFragment::class.java.simpleName
                )
                .commit()
        }
    }
    private fun toHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}