package com.karpov.lab2.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.karpov.lab2.database.Workout
import com.karpov.lab2.databinding.ListItemWorkoutBinding

class WorkoutAdapter(
    private val clickListener: WorkoutListener,
    private val deleteListener: WorkoutDeleteListener
) : ListAdapter<Workout,
        WorkoutAdapter.ViewHolder>(WorkoutDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, deleteListener, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ListItemWorkoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            clickListener: WorkoutListener,
            deteleListener: WorkoutDeleteListener,
            item: Workout
        ) {
            binding.workout = item
            binding.clickListener = clickListener
            binding.deleteListener = deteleListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemWorkoutBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
    override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem.WorkoutId == newItem.WorkoutId
    }

    override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
        return oldItem == newItem
    }
}

class WorkoutListener(val clickListener: (WorkoutId: Long) -> Unit) {
    fun onClick(workout: Workout) = clickListener(workout.WorkoutId)
}

class WorkoutDeleteListener(val deleteListener: (WorkoutId: Long) -> Unit) {
    fun onClick(workout: Workout) = deleteListener(workout.WorkoutId)
}
