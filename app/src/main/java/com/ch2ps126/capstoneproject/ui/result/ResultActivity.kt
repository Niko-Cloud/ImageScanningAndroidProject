package com.ch2ps126.capstoneproject.ui.result

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.ch2ps126.capstoneproject.R
import com.ch2ps126.capstoneproject.databinding.ActivityResultBinding
import com.ch2ps126.capstoneproject.ui.camera.CameraActivity
import com.ch2ps126.capstoneproject.ui.camera.CameraActivity.Companion.CAMERAX_RESULT

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