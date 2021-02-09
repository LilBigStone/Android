package com.karpov.lab3.game

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karpov.lab3.databinding.ListItemGameBinding
import com.karpov.lab3.others.Nucleus

class GameAdapter(
    private val clickListener: GameListener,
) : ListAdapter<Nucleus,
        GameAdapter.ViewHolder>(WorkoutDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: GameListener,
            item: Nucleus
        ) {
            binding.nucleus = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGameBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class WorkoutDiffCallback : DiffUtil.ItemCallback<Nucleus>() {
    override fun areItemsTheSame(oldItem: Nucleus, newItem: Nucleus): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Nucleus, newItem: Nucleus): Boolean {
        return oldItem.data == newItem.data
    }
}

class GameListener(val clickListener: (id: Int) -> Unit) {
    fun onClick(cell: Nucleus) = clickListener(cell.id)
}
