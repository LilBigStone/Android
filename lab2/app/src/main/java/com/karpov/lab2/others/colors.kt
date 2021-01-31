package com.karpov.lab2.others

import android.util.Log
import androidx.lifecycle.MutableLiveData

data class Color(
    var color: String = "",
    var select: Boolean = false
)

fun ListColors(selectColor: String): List<Color> {
    var colors = listOf("#f4acb7", "#7678ed", "#f7b801", "#f35b04", "#f18701", "#70d6ff")
    val list = mutableListOf<Color>()
    for (color in colors) {
        if (selectColor == color) {
            list.add(Color(color, true))
        } else {
            list.add(Color(color, false))
        }
    }
    return list
}