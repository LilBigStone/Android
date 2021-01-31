package com.karpov.lab2.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karpov.lab2.databinding.ListItemColorBinding
import com.karpov.lab2.main.WorkoutListener
import com.karpov.lab2.others.Color

class ColorAdapter(
    private val clickListener: ColorListener
) : ListAdapter<Color,
        ColorAdapter.ViewHolder>(ColorDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: ColorListener,
            item: Color
        ) {
            binding.color = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemColorBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class ColorDiffCallback : DiffUtil.ItemCallback<Color>() {
    override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
        return oldItem.color == newItem.color
    }

    override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
        return oldItem == newItem
    }
}

class ColorListener(val clickListener: (colorString: String) -> Unit) {
    fun onClick(color: Color) = clickListener(color.color)
}

