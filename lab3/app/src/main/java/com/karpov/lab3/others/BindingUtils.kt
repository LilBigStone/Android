package com.karpov.lab3.others
import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("nucleus")
fun TextView.nucleus(nucleus: Nucleus) {
    nucleus.let {
        text = it.data
    }
}
@SuppressLint("SetTextI18n")
@BindingAdapter("stat")
fun TextView.stat(stat: Stat) {
    stat.let {
        text = it.displayNameMy + " " + it.result + " " + it.displayNameOpponent
    }
}