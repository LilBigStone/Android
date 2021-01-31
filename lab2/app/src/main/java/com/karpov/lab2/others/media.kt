package com.karpov.lab2.others

import android.content.Context
import android.media.MediaPlayer
import com.karpov.lab2.R

class Media {

    var mMediaPlayer: MediaPlayer? = null
    fun playTick(context: Context) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.sw)
        mMediaPlayer!!.isLooping = false
        mMediaPlayer!!.start()
    }

    fun playSwitch(context: Context) {
        mMediaPlayer = MediaPlayer.create(context, R.raw.tick)
        mMediaPlayer!!.isLooping = false
        mMediaPlayer!!.start()

    }
}