package com.ch2ps126.capstoneproject.ui.bookmark

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ch2ps126.capstoneproject.data.local.db.entity.Bookmark
import com.ch2ps126.capstoneproject.databinding.ItemFragmentToolsBinding
import com.ch2ps126.capstoneproject.ui.detail.DetailActivity
import com.ch2ps126.capstoneproject.util.Const.DESCRIPTION
import com.ch2ps126.capstoneproject.util.Const.EQUIPMENT_ID
import com.ch2ps126.capstoneproject.util.Const.EQUIPMENT_IMAGE
import com.ch2ps126.capstoneproject.util.Const.NAME
import com.ch2ps126.capstoneproject.util.Const.TARGET_MUSCLE
import com.ch2ps126.capstoneproject.util.Const.TUTORIAL
import com.ch2ps126.capstoneproject.util.Const.VIDEO_TUTORIAL_LINK

class BookmarkAdapter : ListAdapter<Bookmark, BookmarkAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemFragmentToolsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class MyViewHolder(private val binding: ItemFragmentToolsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(equipment: Bookmark) {
            binding.tvItem.text = equipment.name
            val targetMuscle = equipment.targetMuscles
            val array = ArrayList(targetMuscle ?: listOf())
            Glide.with(binding.root)
                .load(equipment.equipmentImage)
                .into(binding.ivItem)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)

                intent.apply {
                    putExtra(EQUIPMENT_ID, equipment.id)
                    putExtra(NAME, equipment.name)
                    putExtra(EQUIPMENT_IMAGE, equipment.equipmentImage)
                    putExtra(DESCRIPTION, equipment.description)
                    putExtra(TARGET_MUSCLE, array)
                    putExtra(TUTORIAL, equipment.tutorial)
                    putExtra(VIDEO_TUTORIAL_LINK, equipment.videoTutorialLink)
                }

//
//                val optionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        itemView.context as Activity,
//                        Pair(binding.tvItem, "profile"),
//                        Pair(binding.tvItemName, "name"),
//                        Pair(binding.tvStoryDescription, "description"),
//                    )
//                itemView.context.startActivity(intent, optionsCompat.toBundle())
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Bookmark>() {
            override fun areItemsTheSame(oldItem: Bookmark, newItem: Bookmark): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Bookmark,
                newItem: Bookmark
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}