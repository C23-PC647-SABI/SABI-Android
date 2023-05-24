package com.example.sabi.ui.onboarding.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sabi.R
import com.example.sabi.databinding.FragmentOnboardingThirdBinding

class OnboardingThirdFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentOnboardingThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_third, container, false)
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}