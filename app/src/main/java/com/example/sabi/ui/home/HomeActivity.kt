package com.example.sabi.ui.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.example.sabi.databinding.ActivityHomeBinding
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.camera.CameraActivity
import com.example.sabi.ui.dictonary.DictonaryActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getPermission()

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
            }
            btnCamera.setOnClickListener{
                val intent = Intent(this@HomeActivity, CameraActivity::class.java)
                startActivity(intent)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getPermission(){
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),101)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
            getPermission()
        }
    }
}