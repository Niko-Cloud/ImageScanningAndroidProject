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

class BookmarkAdapter: ListAdapter<Bookmark, BookmarkAdapter.MyViewHolder>(DIFF_CALLBACK) {
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

        const val EQUIPMENT_ID = "equipment_id"
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val EQUIPMENT_IMAGE = "equipment_image"
        const val TUTORIAL = "tutorial"
        const val TARGET_MUSCLE = "target_muscle"
        const val VIDEO_TUTORIAL_LINK = "video_tutorial_link"
    }
}