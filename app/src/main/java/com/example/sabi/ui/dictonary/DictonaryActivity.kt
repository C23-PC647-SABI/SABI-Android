package com.example.sabi.ui.dictonary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import com.example.sabi.R
import com.example.sabi.databinding.ActivityDictonaryBinding
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.home.HomeActivity
import com.example.sabi.data.repository.Result
import com.example.sabi.model.ResponseListItem

class DictonaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDictonaryBinding
    private val factory = ViewModelFactory.getInstance(this)
    private val dictonaryViewModel: DictonaryViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDictonaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnBack.setOnClickListener{
                val intent = Intent(this@DictonaryActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        dictonaryViewModel.getList().observe(this) {
            when (it) {
                is Result.Error -> showLoading(false)
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    setList(it.data.responseList)
                    showLoading(false)
                }
            }
        }
    }
    private fun setList(data: List<ResponseListItem?>) {
        val adapter = DictonaryAdapter(data)
        binding.rvDictonary.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbar.visibility = View.VISIBLE
        } else {
            binding.progressbar.visibility = View.GONE
        }
    }
}