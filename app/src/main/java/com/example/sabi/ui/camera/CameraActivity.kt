package com.example.sabi.ui.camera

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.sabi.R
import com.example.sabi.databinding.ActivityCameraBinding
import com.example.sabi.ml.Sabimodel
import com.example.sabi.ui.home.HomeActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class CameraActivity : AppCompatActivity() {

    val paint = Paint()

    lateinit var imageProcessor: ImageProcessor
    lateinit var model: Sabimodel
    lateinit var bitmap: Bitmap

    lateinit var handler: Handler
    lateinit var cameraDevice: CameraDevice
    lateinit var cameManager: CameraManager
    lateinit var textureView: TextureView
    private lateinit var binding: ActivityCameraBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var labels = application.assets.open("label.txt").bufferedReader().readLines()

        imageProcessor = ImageProcessor.Builder().add(ResizeOp(224,224,ResizeOp.ResizeMethod.BILINEAR)).build()
        model = Sabimodel.newInstance(this)
        val handlerThread = HandlerThread("videoThread")
        handlerThread.start()
        handler = Handler(handlerThread.looper)

        binding.apply {
            btnBack.setOnClickListener{
                val intent = Intent(this@CameraActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        textureView = findViewById(R.id.textureView)

        paint.setColor(Color.MAGENTA)

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
                bitmap = textureView.bitmap!!
                var tensorImage = TensorImage(DataType.FLOAT32)
                tensorImage.load(bitmap)
                tensorImage = imageProcessor.process(tensorImage)

                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(tensorImage.buffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray

                var maxIdx = 0
                outputFeature0.forEachIndexed{index, fl ->
                    if(fl> 0.9){
                        maxIdx = index
                    }
                }
                Toast.makeText(getApplicationContext(),"output: "+labels[maxIdx],Toast.LENGTH_SHORT).show();

            }

        }

        cameManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

    }

    override fun onDestroy() {
        super.onDestroy()
        model.close()
    }

    fun openCamera(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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