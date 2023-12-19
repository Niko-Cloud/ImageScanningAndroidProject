package com.ch2ps126.tutorin.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ch2ps126.tutorin.R
import com.ch2ps126.tutorin.data.local.db.entity.Bookmark
import com.ch2ps126.tutorin.databinding.ActivityDetailBinding
import com.ch2ps126.tutorin.util.Const.DESCRIPTION
import com.ch2ps126.tutorin.util.Const.EQUIPMENT_ID
import com.ch2ps126.tutorin.util.Const.EQUIPMENT_IMAGE
import com.ch2ps126.tutorin.util.Const.NAME
import com.ch2ps126.tutorin.util.Const.TARGET_MUSCLE
import com.ch2ps126.tutorin.util.Const.TUTORIAL
import com.ch2ps126.tutorin.util.Const.VIDEO_TUTORIAL_LINK
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
        val tutorial: ArrayList<String>? = intent.getStringArrayListExtra(TUTORIAL)
        val videoTutorialLink = intent.getStringExtra(VIDEO_TUTORIAL_LINK)
        val targetMuscles: ArrayList<String>? = intent.getStringArrayListExtra(TARGET_MUSCLE)

        binding.tvTitle.text = name
        binding.tvDescriptionDetail.text = description

        if (tutorial != null) {
            val tutorialAdapter = DetailTutorialAdapter(tutorial)
            binding.tvHowToUse.adapter = tutorialAdapter
            binding.tvHowToUse.layoutManager = LinearLayoutManager(this)
        }
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