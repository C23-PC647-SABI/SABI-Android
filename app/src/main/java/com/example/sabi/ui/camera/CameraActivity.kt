package com.example.sabi.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.AsyncQueryHandler
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
import android.os.FileUtils
import android.os.Handler
import android.os.HandlerThread
import android.view.Surface
import android.view.TextureView
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sabi.R
import com.example.sabi.databinding.ActivityCameraBinding
import com.example.sabi.databinding.ActivityDictonaryBinding
import com.example.sabi.ml.SsdMobilenetV11Metadata1
import com.example.sabi.ml.Sabimodel

import com.example.sabi.ui.ViewModelFactory
import com.example.sabi.ui.home.HomeActivity
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

class CameraActivity : AppCompatActivity() {

    lateinit var labels:List<String>
//    val colors = listOf<Int>(
//        Color.BLUE, Color.GREEN, Color.RED, Color.CYAN, Color.GRAY, Color.BLACK,
//        Color.DKGRAY, Color.MAGENTA, Color.YELLOW, Color.RED
//    )
    val paint = Paint()

    lateinit var imageProcessor: ImageProcessor
    lateinit var model: Sabimodel
    lateinit var bitmap: Bitmap
    lateinit var imageView: ImageView

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

        labels = FileUtil.loadLabels(this,"labels.txt")
        imageProcessor = ImageProcessor.Builder().add(ResizeOp(224,224,ResizeOp.ResizeMethod.BILINEAR)).build()
//        model = SsdMobilenetV11Metadata1.newInstance(this)
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

        imageView = findViewById(R.id.iv_response)
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

                var mutable = bitmap.copy(Bitmap.Config.ARGB_8888,true)
                var canvas = Canvas(mutable)
                var h = bitmap.height
                var w = bitmap.width
                var x = 0



                while (x <= 26){
                    if (outputFeature0.get(x) > 0.45){
                        Toast.makeText(getApplicationContext(),"output: "+outputFeature0.toString(),Toast.LENGTH_SHORT).show();
                    }

                    x+=3
                }

                imageView.setImageBitmap(mutable)

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