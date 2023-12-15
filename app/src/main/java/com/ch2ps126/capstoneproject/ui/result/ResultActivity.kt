package com.ch2ps126.capstoneproject.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.ch2ps126.capstoneproject.databinding.ActivityResultBinding
import com.ch2ps126.capstoneproject.ui.camera.CameraActivity.Companion.EXTRA_CAMERAX_IMAGE
import com.ch2ps126.capstoneproject.ui.detail.DetailActivity
import com.ch2ps126.capstoneproject.ui.home.HomeViewModel
import com.ch2ps126.capstoneproject.ui.home.HomeViewModelFactory
import com.ch2ps126.capstoneproject.util.Const
import com.ch2ps126.capstoneproject.util.ModelProcessing.modelProcess

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var imageView: ImageView
    private lateinit var backButton: CardView

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        imageView = binding.ivCameraResult
        backButton = binding.btnTryAgain

        backButton.setOnClickListener {
            finish()
        }

        homeViewModel.searchQuery.observe(this) {
            homeViewModel.filterEquipment()
        }

        val imageUri = Uri.parse(intent.getStringExtra(EXTRA_CAMERAX_IMAGE))
        imageView.setImageURI(imageUri)

        val score = modelProcess(imageUri, this, contentResolver)


        when {
            score < 0.5 -> {
                homeViewModel.setSearchQuery("benchpress")
            }

            score > 0.5 -> {
                homeViewModel.setSearchQuery("dumbell")
            }

            else -> {
                // do nothing
            }
        }
        homeViewModel.equipmentData.observe(this) { equipment ->
            Glide.with(binding.root)
                .load(equipment[0].equipmentImage)
                .into(binding.ivResult)
            binding.tvResultName.text = equipment[0].name
            val targetMuscle = equipment[0].targetMuscles
            val array = ArrayList(targetMuscle ?: listOf())
            val intent = Intent(this, DetailActivity::class.java)
            intent.apply {
                putExtra(Const.EQUIPMENT_ID, equipment[0].equipmentId)
                putExtra(Const.NAME, equipment[0].name)
                putExtra(Const.EQUIPMENT_IMAGE, equipment[0].equipmentImage)
                putExtra(Const.DESCRIPTION, equipment[0].description)
                putExtra(Const.TARGET_MUSCLE, array)
                putExtra(Const.TUTORIAL, equipment[0].tutorial)
                putExtra(Const.VIDEO_TUTORIAL_LINK, equipment[0].videoTutorialLink)
            }
            binding.cardView2.setOnClickListener {
                startActivity(intent)
            }
        }
    }
}