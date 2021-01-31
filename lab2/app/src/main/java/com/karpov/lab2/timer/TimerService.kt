package com.karpov.lab2.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.karpov.lab2.MainActivity
import com.karpov.lab2.R
import com.karpov.lab2.database.Step
import com.karpov.lab2.database.WorkoutDatabase
import com.karpov.lab2.database.WorkoutDatabaseDao
import com.karpov.lab2.others.Constants.ACTION_BACK_BUTTON
import com.karpov.lab2.others.Constants.ACTION_NEXT_BUTTON
import com.karpov.lab2.others.Constants.ACTION_PAUSE_BUTTON
import com.karpov.lab2.others.Constants.ACTION_START_OR_RESUME_SERVICE
import com.karpov.lab2.others.Constants.ACTION_STOP_SERVICE
import com.karpov.lab2.others.Constants.ACTION_TIMER_FRAGMENT
import com.karpov.lab2.others.Constants.NOTIFICATION_CHANNEL_ID
import com.karpov.lab2.others.Constants.NOTIFICATION_CHANNEL_NAME
import com.karpov.lab2.others.Constants.NOTIFICATION_ID
import com.karpov.lab2.others.Media
import kotlinx.coroutines.*

class TimerService : LifecycleService() {


    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var timer: CountDownTimer
    var killed = false
    var first = true


    companion object {
        const val DONE = 0L
        const val ONE_SECOND = 1000L
        var COUNTDOWN_TIME = 10000L
        val media = Media()
        var pause = MutableLiveData<Boolean>()
        var steps = MutableLiveData<List<Step>>()
        var i = MutableLiveData<Int>()
        var key = MutableLiveData<Long>()
        var title = MutableLiveData<String>()
        var color = MutableLiveData<String>()
        val time = MutableLiveData<Long>()
        lateinit var database: WorkoutDatabaseDao
    }


    override fun onCreate() {
        super.onCreate()

        time.observe(this, { second ->
            if (second == 0L) {
                media.playSwitch(this)
            }
            if (second in 1..3) {
                media.playTick(this)
            }
        })
        database = WorkoutDatabase.getInstance(application).workoutDatabaseDao
        scope.launch {
            steps.value = key.value?.let { database.getSteps(key.value!!) }
        }
        key.observe(this, {
            scope.launch {
                steps.value = key.value?.let { database.getSteps(key.value!!) }
            }
        })
    }

    init {
        timer = object : CountDownTimer(0L, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                time.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                time.value = DONE
            }
        }
        i.value = -1
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        key.value = intent?.getLongExtra("key", 0L)!!
        intent.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (first) {
                        startForegroundService()
                        first = false
                        nextStep()
                    }
                }
                ACTION_NEXT_BUTTON -> {
                    nextStep()
                }
                ACTION_BACK_BUTTON -> {
                    backStep()
                }
                ACTION_PAUSE_BUTTON -> {
                    onStop()
                }
                ACTION_STOP_SERVICE -> {
                    stopService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startTimer() {
        val step = steps.value?.get(i.value!!)
        if (step != null) {
            title.value = resources.getString(step.name)
            COUNTDOWN_TIME = step.time.toLong() * 1000
            color.value = step.color
        }
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                time.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                nextStep()
            }
        }
        timer.start()
    }


    private fun nextStep() {
        timer.cancel()
        if (steps.value?.size == i.value!! + 1) {
            time.value = DONE
            stopService()
            return
        }
        i.value = i.value!!.inc()
        startTimer()
    }

    private fun backStep() {
        timer.cancel()
        if (i.value!! == 0) {
            time.value = DONE
            return
        }
        i.value = i.value!!.dec()
        startTimer()
    }

    private fun onStop() {
        if (pause.value == false) {
            pause.value = true
            timer.cancel()
        } else {
            pause.value = false
            timer.start()
        }
    }


    private fun startForegroundService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(notificationManager)
        val notificationBuilded = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setContentTitle(resources.getString(R.string.app_name))
            .setOngoing(true)
            .setColorized(true)
            .setContentText(Observer<MutableLiveData<Int>> {
                time.value
            }.toString())
            .setContentIntent(getMainActivityPendingIntent())

        time.observe(this, {
            notificationBuilded.setContentText(time.value?.let { it1 ->
                DateUtils.formatElapsedTime(
                    it1
                )
            })
            startForeground(NOTIFICATION_ID, notificationBuilded.build())
        })
        title.observe(this, {
            notificationBuilded.setContentTitle(title.value.toString())
            startForeground(NOTIFICATION_ID, notificationBuilded.build())
        })
        color.observe(this, {
            notificationBuilded.color = Color.parseColor(color.value)
            startForeground(NOTIFICATION_ID, notificationBuilded.build())
        })
        startForeground(NOTIFICATION_ID, notificationBuilded.build())

    }


    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = ACTION_TIMER_FRAGMENT
        },
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
        notificationManager.createNotificationChannel(channel)

    }

    private fun stopService() {
        stopForeground(true)
        killed = true
        first = true
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
