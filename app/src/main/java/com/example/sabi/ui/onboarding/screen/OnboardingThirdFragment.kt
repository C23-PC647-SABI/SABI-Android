package com.example.sabi.ui.onboarding.screen

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sabi.R
import com.example.sabi.databinding.FragmentOnboardingThirdBinding
import com.example.sabi.ui.home.HomeActivity
import com.example.sabi.ui.main.MainViewModel

class OnboardingThirdFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentOnboardingThirdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext: ImageButton = view.findViewById(R.id.btn_next)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_next) {
                toHome()
            }
        }
    }
    private fun toHome() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        getActivity()?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}