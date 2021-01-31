package com.karpov.lab2.database

import androidx.room.*


data class WorkoutAndSteps(
    @Embedded val title: Workout,
    @Relation(
        parentColumn = "WorkoutId",
        entityColumn = "WorkoutCreatorId"
    )
    val playlists: List<Step>
)
