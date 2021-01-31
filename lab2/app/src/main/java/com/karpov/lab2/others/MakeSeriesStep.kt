package com.karpov.lab2.others

import com.karpov.lab2.R
import com.karpov.lab2.database.Step
import com.karpov.lab2.database.Workout

fun makeSeriesStep(workout: Workout): MutableList<Step> {
    val steps: MutableList<Step> = mutableListOf()

    steps.add(
        Step(
            name = R.string.ready,
            time = workout.readyTime,
            WorkoutCreatorId = workout.WorkoutId,
            color = "#f4f1de"
        )
    )
    for (i in 0..workout.cycles + 1) {
        if (i % 2 != 0) {
            steps.add(
                Step(
                    name = R.string.work,
                    time = workout.workTime,
                    WorkoutCreatorId = workout.WorkoutId,
                    color = "#e07a5f"
                )
            )
        } else {
            steps.add(
                Step(
                    name = R.string.chill,
                    time = workout.chill,
                    WorkoutCreatorId = workout.WorkoutId,
                    color = "#81b29a"
                )
            )
        }
    }
    steps.add(
        Step(
            name = R.string.finish,
            time = 0,
            WorkoutCreatorId = workout.WorkoutId,
            color = "#f2cc8f"
        )
    )
    return steps
}