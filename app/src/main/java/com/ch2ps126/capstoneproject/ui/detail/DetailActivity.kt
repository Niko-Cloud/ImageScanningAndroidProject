package com.ch2ps126.capstoneproject.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ch2ps126.capstoneproject.R
import com.ch2ps126.capstoneproject.data.local.db.entity.Bookmark
import com.ch2ps126.capstoneproject.databinding.ActivityDetailBinding
import com.ch2ps126.capstoneproject.util.Const.DESCRIPTION
import com.ch2ps126.capstoneproject.util.Const.EQUIPMENT_ID
import com.ch2ps126.capstoneproject.util.Const.EQUIPMENT_IMAGE
import com.ch2ps126.capstoneproject.util.Const.NAME
import com.ch2ps126.capstoneproject.util.Const.TARGET_MUSCLE
import com.ch2ps126.capstoneproject.util.Const.TUTORIAL
import com.ch2ps126.capstoneproject.util.Const.VIDEO_TUTORIAL_LINK
import com.google.android.material.chip.Chip

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val equipmentId = intent.getIntExtra(EQUIPMENT_ID, 0)
        val name = intent.getStringExtra(NAME)
        val equipmentImage = intent.getStringExtra(EQUIPMENT_IMAGE)
        val description = intent.getStringExtra(DESCRIPTION)
        val tutorial = intent.getStringExtra(TUTORIAL)
        val videoTutorialLink = intent.getStringExtra(VIDEO_TUTORIAL_LINK)
        val targetMuscles: ArrayList<String?>? = intent.getStringArrayListExtra(TARGET_MUSCLE)


        binding.tvTitle.text = name
        binding.tvDescriptionDetail.text = description
        binding.tvHowToUse.text = tutorial
        Glide.with(this@DetailActivity)
            .load(equipmentImage)
            .into(binding.ivTools)
        if (targetMuscles != null) {
            for (tag in targetMuscles) {
                val chip = Chip(this@DetailActivity)
                chip.text = tag
                binding.chipGroup.addView(chip)
            }
        }
        binding.tvLinkVideo.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(videoTutorialLink)
            startActivity(intent)
        }

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        viewModel.getBookmarkExists(equipmentId).observe(this) { curr ->
            Log.d("DetailActivity", "curr: $curr")
            val newBookmark = Bookmark(
                id = equipmentId,
                name = name,
                equipmentImage = equipmentImage,
                description = description,
                tutorial = tutorial,
                targetMuscles = targetMuscles,
                videoTutorialLink = videoTutorialLink
            )
            binding.ivBookmark.setImageResource(
                if (curr) R.drawable.bookmark_button_filled else R.drawable.bookmark_button_outline
            )
            binding.ivBookmark.setOnClickListener {
                if (curr) {
                    viewModel.deleteBookmark(equipmentId)
                } else {
                    viewModel.insertBookmark(newBookmark)
                }
            }

        }
    }
}