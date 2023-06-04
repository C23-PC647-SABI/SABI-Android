package com.example.sabi.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sabi.R
import com.example.sabi.databinding.ActivityCameraBinding
import com.example.sabi.databinding.ActivityDictonaryBinding
import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.home.HomeActivity

class CameraActivity : AppCompatActivity() {

    lateinit var cameraDevice: CameraDevice
    lateinit var handler: Handler
    lateinit var cameManager: CameraManager
    lateinit var textureView: TextureView
    private lateinit var binding: ActivityCameraBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        cameManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        binding.apply {
            btnBack.setOnClickListener{
                val intent = Intent(this@CameraActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        textureView = findViewById(R.id.textureView)
        textureView.surfaceTextureListener = object:TextureView.SurfaceTextureListener{
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                openCamera()
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                return false
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

        }


    }

    fun openCamera(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        cameManager.openCamera(cameManager.cameraIdList[0],object:CameraDevice.StateCallback(){
            override fun onOpened(camera: CameraDevice) {
                cameraDevice = camera

                var surfaceTexture = textureView.surfaceTexture
                var surface = Surface(surfaceTexture)

                var captureRequest = cameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
                captureRequest.addTarget(surface)

                cameraDevice.createCaptureSession(listOf(surface),object: CameraCaptureSession.StateCallback(){
                    override fun onConfigured(session: CameraCaptureSession) {
                        session.setRepeatingRequest(captureRequest.build(),null,null)
                    }

                    override fun onConfigureFailed(session: CameraCaptureSession) {

                    }

                },handler)
            }

            override fun onDisconnected(camera: CameraDevice) {

            }

            override fun onError(camera: CameraDevice, error: Int) {

            }
        },handler)
    }
}