package com.example.sabi.ui.onboarding.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sabi.R
import com.example.sabi.databinding.FragmentOnboardingFirstBinding

class OnboardingFirstFragment : Fragment(), View.OnClickListener{

    private var _binding: FragmentOnboardingFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnNext: ImageButton = view.findViewById(R.id.btn_next)
        btnNext.setOnClickListener(this)
        val btnSkip: TextView = view.findViewById(R.id.btn_skip)
        btnSkip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            if (v.id == R.id.btn_next) {
                val secondOnboardingFragment = OnboardingSecondFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, secondOnboardingFragment, OnboardingSecondFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }else if (v.id == R.id.btn_skip) {
                val thirdOnboardingFragment = OnboardingThirdFragment()
                val fragmentManager = parentFragmentManager
                fragmentManager.beginTransaction().apply {
                    replace(R.id.frame_container, thirdOnboardingFragment, OnboardingThirdFragment::class.java.simpleName)
                    addToBackStack(null)
                    commit()
                }
            }
        }
    }

}