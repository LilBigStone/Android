package com.karpov.lab2.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.karpov.lab2.database.Workout
import com.karpov.lab2.database.WorkoutDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainViewModel(
    val database: WorkoutDatabaseDao,
    application: Application
) : AndroidViewModel(application) {


    val listWorkout = database.getWorkouts()
    var colors = listOf("#f4acb7", "#7678ed", "#f7b801", "#f35b04", "#f18701", "#70d6ff")
    private val _navigateToWorkoutDetail = MutableLiveData<Long>()
    val navigateToWorkoutDetail
        get() = _navigateToWorkoutDetail

    fun onWorkoutClicked(id: Long) {
        _navigateToWorkoutDetail.value = id
    }

    fun onNavigated() {
        _navigateToWorkoutDetail.value = null
    }

    fun deleteWorkout(id: Long) {
        viewModelScope.launch {
            delete(id)
        }
    }

    fun onCreateWorkout() {
        viewModelScope.launch {
            create(Workout(color = colors[Random().nextInt(6)]))
        }
    }

    private suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            database.deleteWorkout(id)
        }
    }

    private suspend fun create(workout: Workout) {
        withContext(Dispatchers.IO) {
            database.insertWorkout(workout)
        }
    }
}