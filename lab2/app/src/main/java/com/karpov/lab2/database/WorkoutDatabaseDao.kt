package com.karpov.lab2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.karpov.lab2.database.Step
import com.karpov.lab2.database.Workout
import com.karpov.lab2.database.WorkoutAndSteps

@Dao
interface WorkoutDatabaseDao {

    @Insert
    suspend fun insertWorkout(title: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

    @Query("SELECT * FROM step_table WHERE WorkoutCreatorId = :key")
    suspend fun getSteps(key: Long): List<Step>

    @Query("DELETE FROM step_table WHERE WorkoutCreatorId = :key")
    suspend fun deleteSteps(key: Long)

    @Query("DELETE FROM workout_table WHERE WorkoutId = :key ")
    suspend fun deleteWorkout(key: Long)

    @Query("SELECT * from workout_table WHERE WorkoutId = :key")
    suspend fun getTitle(key: Long): Workout?

    @Query("SELECT * FROM workout_table ORDER BY WorkoutId DESC")
    fun getWorkouts(): LiveData<List<Workout>>

    @Query("SELECT * from workout_table WHERE WorkoutId = :key")
    fun getWorkoutWithId(key: Long): LiveData<Workout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListSteps(objects: List<Step>)

    @Transaction
    @Query("SELECT * FROM workout_table")
    fun getUsersWithPlaylists(): List<WorkoutAndSteps>

    @Query("DELETE FROM workout_table")
    suspend fun cleanData()
}


