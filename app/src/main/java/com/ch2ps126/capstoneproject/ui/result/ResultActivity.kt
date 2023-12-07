package com.ch2ps126.capstoneproject.ui.result

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.ch2ps126.capstoneproject.databinding.ActivityResultBinding
import com.ch2ps126.capstoneproject.ui.camera.CameraActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentImageUri = intent.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.ivCameraResult.setImageURI(it)
        }
    }
}