package com.example.tomato

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import android.os.CountDownTimer
import android.os.health.TimerStat
import android.widget.ProgressBar
import android.widget.TextView

class ProgressTask: AsyncTask<Context, Int, Int>() {
    lateinit var progressBar : ProgressBar
    lateinit var textView : TextView

    //enum class TimerState{
    //Stopped,Paused,Running
    //}
    //private lateinit var timer:CountDownTimer
    //private var timerLengthSecond=0L
    //private var timerState=TimerState.Stopped
    //private var secondsRemaining=0L

    @SuppressLint("WrongThread")
    override fun doInBackground(vararg context: Context): Int {
        while ( progressBar.progress < progressBar.max ){
            Thread.sleep(1000)
            progressBar.incrementProgressBy(1)
            publishProgress()

        }
        return progressBar.progress
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        //textView.setText("${progressBar.progress} %")
    }
}