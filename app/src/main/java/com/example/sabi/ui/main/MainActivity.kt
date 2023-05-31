package com.example.sabi.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.example.sabi.R
import com.example.sabi.ui.onboarding.screen.OnboardingFirstFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}