package com.karpov.lab2.detail

import android.util.Log
import androidx.lifecycle.*
import com.karpov.lab2.database.Workout
import com.karpov.lab2.database.WorkoutDatabaseDao
import com.karpov.lab2.others.Color
import com.karpov.lab2.others.ListColors
import com.karpov.lab2.others.makeSeriesStep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(
    workoutKey: Long = 0L,
    dataSource: WorkoutDatabaseDao
) : ViewModel() {
    val database = dataSource
    var workout = MutableLiveData(Workout())
    var workoutTime = database.getWorkoutWithId(workoutKey)
    var selectColor = MutableLiveData<List<Color>>()
    var name = MutableLiveData<String>()

    private val _navigateToTimer = MutableLiveData<Long>()
    val navigateToTimer
        get() = _navigateToTimer

    fun onNavigated() {
        _navigateToTimer.value = null
    }

    fun onColorSelect(color: String) {
        workout.value?.color = color
        selectColor.value = ListColors(workout.value?.color!!)
        selectColor.value = selectColor.value
    }

    fun init() {
        Log.i("ColorAdapter", selectColor.value?.size.toString())
        if (workoutTime.value?.WorkoutId != null) {
            workout.value?.WorkoutId = workoutTime.value?.WorkoutId!!
            name.value = workoutTime.value?.nameTimer.toString()
            workout.value?.readyTime = workoutTime.value?.readyTime!!
            workout.value?.workTime = workoutTime.value?.workTime!!
            workout.value?.chill = workoutTime.value?.chill!!
            workout.value?.cycles = workoutTime.value?.cycles!!
            workout.value?.color = workoutTime.value?.color!!
            workout.value = workout.value
            selectColor.value = ListColors(workout.value?.color!!)
        }
    }


    fun startTimer(workoutKey: Long) {
        _navigateToTimer.value = workoutKey
    }

    fun deleteSteps() {
        viewModelScope.launch(Dispatchers.IO) {
            workout.value?.WorkoutId?.let { database.deleteSteps(it) }
        }
    }

    fun update(title: String) {
        deleteSteps()
        viewModelScope.launch(Dispatchers.IO) {
            workout.value?.nameTimer = title
            workout.value?.let { database.updateWorkout(workout = it) }
        }
        val steps = workout.value?.let { makeSeriesStep(workout = it) }
        viewModelScope.launch(Dispatchers.IO) {
            if (steps != null) {
                database.insertListSteps(steps)
            }
        }
    }


}