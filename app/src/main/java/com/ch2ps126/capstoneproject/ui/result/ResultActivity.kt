package com.ch2ps126.capstoneproject.ui.result

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ch2ps126.capstoneproject.databinding.ActivityResultBinding
import com.ch2ps126.capstoneproject.ml.BirdModel
import com.ch2ps126.capstoneproject.ml.EquipmentModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer

class ResultActivity : AppCompatActivity() {


    private lateinit var binding: ActivityResultBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private lateinit var tvOutput: TextView


    private var currentImageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageView = binding.imageView
        button = binding.btnCaptureImage
        tvOutput = binding.tvOutput

        val buttonLoad = binding.btnLoadImage


        buttonLoad.setOnClickListener {

            startGallery()

        }
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showToast("Permission request granted")
        } else {
            showToast("Permission request denied")
        }
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let { uri ->
            try {
                val model = EquipmentModel.newInstance(this)

                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Resize the bitmap to match the model's expected input size (640x640)
                val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 640, 640, true)

                // Convert the resized bitmap to a ByteBuffer
                val byteBuffer = resizedBitmap.toByteBuffer()

                // Create TensorBuffer and load the ByteBuffer
                val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 640, 640, 3), DataType.FLOAT32)
                inputFeature0.loadBuffer(byteBuffer)

                // Run model inference
                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer

                // Process the output as needed
                val max = outputFeature0.floatArray.toString()
                Log.d("Result", max.toString())
                Log.d("Result", outputFeature0.toString())

                // Display image in the imageView
                imageView.setImageBitmap(resizedBitmap)

                // Close the model to release resources
                model.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
    private fun Bitmap.toByteBuffer(): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * width * height * 3)
        byteBuffer.rewind()
        copyPixelsToBuffer(byteBuffer)
        return byteBuffer
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}