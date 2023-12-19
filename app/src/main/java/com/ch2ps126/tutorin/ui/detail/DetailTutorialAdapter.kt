package com.ch2ps126.tutorin.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ch2ps126.tutorin.R

class DetailTutorialAdapter(private val tutorialList: List<String>) :
    RecyclerView.Adapter<DetailTutorialAdapter.TutorialViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TutorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.how_to_use_item, parent, false)
        return TutorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: TutorialViewHolder, position: Int) {
        val tutorialStep = tutorialList[position]
        holder.bind(tutorialStep, position)
    }

    override fun getItemCount(): Int {
        return tutorialList.size
    }

    inner class TutorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberTextView: TextView = itemView.findViewById(R.id.tv_number)
        private val descriptionTextView: TextView =
            itemView.findViewById(R.id.tv_how_to_use_description)

        fun bind(tutorialStep: String, position: Int) {
            numberTextView.text = (position + 1).toString() // Incrementing the step number
            descriptionTextView.text = tutorialStep
        }
    }
}