package com.karpov.lab3.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karpov.lab3.databinding.ListItemGameBinding
import com.karpov.lab3.databinding.ListItemStatBinding
import com.karpov.lab3.others.Stat

class StatAdapter() : ListAdapter<Stat,
        StatAdapter.ViewHolder>(WorkoutDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind( item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemStatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Stat
        ) {
            binding.stat = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemStatBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class WorkoutDiffCallback : DiffUtil.ItemCallback<Stat>() {
    override fun areItemsTheSame(oldItem: Stat, newItem: Stat): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Stat, newItem: Stat): Boolean {
        return oldItem == newItem
    }
}


