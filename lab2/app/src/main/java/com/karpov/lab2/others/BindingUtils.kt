package com.karpov.lab2.others

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.BindingAdapter
import com.karpov.lab2.R
import com.karpov.lab2.database.Workout

@BindingAdapter("title")
fun TextView.title(item: Workout?) {
    item?.let {
        text = item.nameTimer
    }
}

@BindingAdapter("ready")
fun TextView.ready(item: Workout?) {
    item?.let {
        text = item.readyTime.toString()
    }
}

@BindingAdapter("work")
fun TextView.work(item: Workout?) {
    item?.let {
        text = item.workTime.toString()
    }
}

@BindingAdapter("chill")
fun TextView.chill(item: Workout?) {
    item?.let {
        text = item.chill.toString()
    }
}

@BindingAdapter("cycles")
fun TextView.cycles(item: Workout?) {
    item?.let {
        text = item.cycles.toString()
    }
}

@SuppressLint("Range")
@BindingAdapter("color")
fun ImageButton.color(item: com.karpov.lab2.others.Color) {
    item?.let {

        if (item.select) {
            val unwrappedDrawable =
                AppCompatResources.getDrawable(context, R.drawable.ic_check_mark)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color))
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable
        } else {
            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.item_shape)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, Color.parseColor(item.color))
            setBackgroundColor(Color.parseColor(item.color))
            background = wrappedDrawable
        }
    }
}

@SuppressLint("UseCompatLoadingForDrawables")
@BindingAdapter("color")
fun ConstraintLayout.color(item: Workout?) {
    item?.let {
        val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.shape)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(
            DrawableCompat.wrap(unwrappedDrawable!!),
            Color.parseColor(item.color)
        )
        background = wrappedDrawable
    }
}

