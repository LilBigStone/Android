package com.karpov.lab2.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.karpov.lab2.database.WorkoutDatabaseDao

class DetailViewModelFactory(
    private val WorkoutKey: Long,
    private val dataSource: WorkoutDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(WorkoutKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}