package com.ch2ps126.tutorin.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2ps126.tutorin.data.remote.response.EquipmentResponseItem
import com.ch2ps126.tutorin.databinding.ItemFragmentToolsBinding
import com.ch2ps126.tutorin.ui.detail.DetailActivity
import com.ch2ps126.tutorin.util.Const.DESCRIPTION
import com.ch2ps126.tutorin.util.Const.EQUIPMENT_ID
import com.ch2ps126.tutorin.util.Const.EQUIPMENT_IMAGE
import com.ch2ps126.tutorin.util.Const.NAME
import com.ch2ps126.tutorin.util.Const.TARGET_MUSCLE
import com.ch2ps126.tutorin.util.Const.TUTORIAL
import com.ch2ps126.tutorin.util.Const.VIDEO_TUTORIAL_LINK

class HomeAdapter : ListAdapter<EquipmentResponseItem, HomeAdapter.MyViewHolder>(DIFF_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemFragmentToolsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder( private val binding: ItemFragmentToolsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: EquipmentResponseItem) {
            binding.tvItem.text = equipment.name
            val targetMuscles = equipment.muscles
            val tutorial = equipment.tutorial

            val muscleNames = targetMuscles?.joinToString(", ") { it?.targetMuscleName ?: "" }
            Glide.with(binding.root)
                .load(equipment.equipmentImage)
                .into(binding.ivItem)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)

                intent.apply {
                    putExtra(EQUIPMENT_ID, equipment.equipmentId)
                    putExtra(NAME, equipment.name)
                    putExtra(EQUIPMENT_IMAGE, equipment.equipmentImage)
                    putExtra(DESCRIPTION, equipment.description)
                    putStringArrayListExtra(TARGET_MUSCLE,
                        muscleNames?.split(", ")?.let { it1 -> ArrayList(it1) })
                    putStringArrayListExtra(TUTORIAL, tutorial?.let { it1 -> ArrayList(it1) })
                    putExtra(VIDEO_TUTORIAL_LINK, equipment.videoTutorialLink)
                }
                itemView.context.startActivity(intent)

            }
        }
    }



    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<EquipmentResponseItem>() {
            override fun areItemsTheSame(oldItem: EquipmentResponseItem, newItem: EquipmentResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: EquipmentResponseItem,
                newItem: EquipmentResponseItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}