package com.ch2ps126.capstoneproject.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.ch2ps126.capstoneproject.R
import com.ch2ps126.capstoneproject.databinding.ActivityDetailBinding
import com.ch2ps126.capstoneproject.ui.home.HomeAdapter.Companion.EQUIPMENT_ID
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel> {
        DetailViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val equipmentID = intent.getIntExtra(EQUIPMENT_ID, 0)

        supportActionBar?.hide()

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.ivBookmark.setOnClickListener{

        }

        lifecycleScope.launch {
            viewModel.getEquipmentById(equipmentID)
            viewModel.equipmentData.observe(this@DetailActivity) { equipment ->
                equipment?.let {
                    binding.tvTitle.text = equipment.name
                    binding.tvDescriptionDetail.text = equipment.description
                    binding.tvHowToUse.text = equipment.tutorial
                    Glide.with(this@DetailActivity)
                        .load(equipment.equipmentImage)
                        .into(binding.ivTools)
                    for (tag in equipment.targetMuscles!!){
                        val chip = Chip(this@DetailActivity)
                        chip.text = tag
                        binding.chipGroup.addView(chip)
                    }
                }
            }
        }

    }
}