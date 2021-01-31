package com.karpov.lab2.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout(

    @PrimaryKey(autoGenerate = true)
    var WorkoutId: Long = 0L,

    @ColumnInfo(name = "name")
    var nameTimer: String = "Workout",

    @ColumnInfo(name = "ready")
    var readyTime: Int = 15,

    @ColumnInfo(name = "work")
    var workTime: Int = 50,

    @ColumnInfo(name = "chill")
    var chill: Int = 25,

    @ColumnInfo(name = "cycle")
    var cycles: Int = 1,

    @ColumnInfo(name = "color")
    var color: String = "#388310"

)