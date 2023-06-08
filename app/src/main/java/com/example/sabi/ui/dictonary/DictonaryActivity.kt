package com.example.sabi.ui.dictonary

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sabi.R
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.home.HomeActivity
import com.example.sabi.data.repository.Result
import com.example.sabi.databinding.ActivityDictonaryBinding
import com.example.sabi.model.ResponseListItem

class DictonaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDictonaryBinding
    private val factory = ViewModelFactory.getInstance(this)
    private val dictonaryViewModel: DictonaryViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityDictonaryBinding.inflate(layoutInflater)

        val layoutManager = GridLayoutManager(this, 3)
//        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)

        binding.apply {
            rvDictonary.layoutManager = layoutManager
//            rvDictonary.addItemDecoration(itemDecoration)
            btnBack.setOnClickListener{
                val intent = Intent(this@DictonaryActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        dictonaryViewModel.getList().observe(this) {
            when (it) {
                is Result.Error -> showLoading(false)
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    setList(it.data)
                    showLoading(false)
                }
            }
        }
        setContentView(binding.root)
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun setList(data: List<ResponseListItem?>) {
        val adapter = DictonaryAdapter(data)
        adapter.notifyDataSetChanged()
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