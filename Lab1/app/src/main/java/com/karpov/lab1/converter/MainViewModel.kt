package com.karpov.lab1.converter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.karpov.lab1.others.converterLogic

class MainViewModel : ViewModel() {
    var data = MutableLiveData<String>()
    var result = MutableLiveData<String>()

    init {
        data.value = ""
        result.value = ""
    }

    fun logic(input: String, output: String, mainCategory: String) {
        if (data.value!!.isNotEmpty()) {
            result.value =
                converterLogic(input, output, data.value!!.toDouble(), mainCategory).toString()
        }
    }

    fun button(number: Char) {
        data.value = data.value + number.toString()
    }

    fun dot() {
        if (data.value?.contains(".") == false && data.value?.length != 0) {
            data.value = data.value + "."
        }
    }

    fun clear() {
        data.value = ""
        result.value = ""
    }

    fun delete() {
        data.value = data.value?.dropLast(1)
    }
}