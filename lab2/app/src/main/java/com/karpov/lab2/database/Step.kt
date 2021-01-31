package com.karpov.lab2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_table")
data class Step(

    @PrimaryKey(autoGenerate = true)
    var StepId: Long = 0L,
    var WorkoutCreatorId: Long,
    var name: Int,
    var color: String,
    var time: Int,

    )