package com.karpov.lab3.others

import com.karpov.lab3.others.Constants.DRAW
import com.karpov.lab3.others.Constants.EMPTY

fun game(list: List<Nucleus>): String {

    val matrix = MutableList(3) { MutableList(3) { Nucleus() } }
    matrix[0] = mutableListOf(list[0], list[1], list[2])
    matrix[1] = mutableListOf(list[3], list[4], list[5])
    matrix[2] = mutableListOf(list[6], list[7], list[8])
    var draw = true
    for (i in 0..2) {
        if (matrix[i][0].data == matrix[i][1].data &&
            matrix[i][1].data == matrix[i][2].data &&
            matrix[i][0].data == matrix[i][2].data &&
            matrix[i][2].data.isNotEmpty()
        ) {
            return matrix[i][2].data
        }
        if (matrix[0][i].data == matrix[1][i].data &&
            matrix[1][i].data == matrix[2][i].data &&
            matrix[0][i].data == matrix[2][i].data &&
            matrix[2][i].data.isNotEmpty()
        ) {

            return matrix[2][i].data
        }
    }


    for (i in 0..2) {
        for (j in 0..2) {
            if (matrix[i][j].data.isEmpty()) {
                draw = false
            }
        }
    }
    if (draw) {
        return DRAW
    }

    return EMPTY

}
