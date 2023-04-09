package com.example.tomato

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.mikhaellopez.circularimageview.CircularImageView
import kotlinx.android.synthetic.main.activity_main.*
import pl.droidsonroids.gif.GifImageView
import kotlin.properties.Delegates


@TargetApi(Build.VERSION_CODES.O)
class MainActivity : AppCompatActivity() {

    lateinit var progressTask: ProgressTask //進度條宣告
    private lateinit var timer:CountDownTimer //倒數宣告
    var temptime:Long=250000 //倒數值
    var backtime="00:00:25"
    var changeimageposition:Int=0 //用來判斷甚麼時候要換圖片(宣告

    lateinit var play: Button
    lateinit var back: Button
    lateinit var gifimageview: GifImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()


        play=findViewById(R.id.play)
        back=findViewById(R.id.back)
        back.isEnabled=false
        back.isClickable = false


        gifimageview=findViewById(R.id.gifImageView)
        setimage()     //呼叫函式;顯示第一張靜態圖

        initProgressTask()    //設定進度條初始狀態
        play.setOnClickListener(mOnClickListener)  //監聽開始按鍵
        back.setOnClickListener(mOnClickListener)  //監聽重來按鍵
        progressBar.max=50    //設定初始的進度條最大值

        //工作50秒;休息10秒鐘(DEMO用)
        //工作25分;休息5分鐘
        //工作50分;休息10分鐘
        //工作1時25分;休息5分鐘
        //工作1時50分;休息10分鐘

        val time = arrayListOf("00:00:25", "00:25:00", "00:50:00", "01:25:00", "01:50:00")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, time)
        timechoice.adapter = adapter


        //以下是監聽選取設定時間Spinner時;所要做出的事件處理
        timechoice.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position==0){
                    textView.text = "時間: 00:00:25" //OKOK
                    progressBar.max=25-1
                    temptime=25000
                    backtime="時間: 00:00:25"
                    changeimageposition=0

                }else if(position==1){
                    textView.text = "時間: 00:25:00"
                    progressBar.max=1500-60+4 //OKOK
                    temptime=1500000
                    backtime="時間: 00:25:00"
                    changeimageposition=1

                }else if(position==2){
                    textView.text = "時間: 00:50:00"
                    progressBar.max=3000-120+6 //OKOK
                    temptime=3000000
                    backtime="時間: 00:50:00"
                    changeimageposition=2

                }else if(position==3){
                    textView.text = "時間: 01:25:00"
                    progressBar.max=5100-204+13 //OKOK
                    temptime=5100000
                    backtime="時間: 01:25:00"
                    changeimageposition=3

                }else if(position==4){
                    textView.text = "時間: 01:50:00"
                    progressBar.max=6600-264+15 //OKOK
                    temptime=6600000
                    backtime="時間: 01:50:00"
                    changeimageposition=4
                }
            }
        }
    }


    //以下setimage函式是用來轉換圖片或動圖;並將圖片形狀切成圓形
    fun setimage(){
        Glide.with(this)
            .load(R.drawable.tomatoclock)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(gifimageview)
    }
    fun setimage1(){
        Glide.with(this)
            .load(R.drawable.cooking1)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(gifimageview)
    }
    fun setimage2(){
        Glide.with(this)
            .load(R.drawable.cooking2)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(gifimageview)
    }
    fun setimagefalse(){
        Glide.with(this)
            .load(R.drawable.cookingfalse)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(gifimageview)
    }



    //以下是用來監聽按下button後,進度條同時開始運作,顯示倒數的數字
    val mOnClickListener = View.OnClickListener { it ->
        when (it) {
            //按下開始button後要做的事件處理
            play -> {
                play.isEnabled=false
                play.isClickable = false
                back.isEnabled=true
                back.isClickable = true
                setimage1()
                timechoice.isEnabled=false

                if(progressTask.status == AsyncTask.Status.RUNNING){
                    Toast.makeText(this, "程序已在執行中", Toast.LENGTH_LONG).show()
                }
                else progressTask.execute(this)

                timer=object : CountDownTimer(temptime+10+1000, 1000) {
                    @SuppressLint("SetTextI18n")
                    override fun onTick(millisUntilFinished: Long) {
                        var second: Int = (Math.round(millisUntilFinished.toDouble() / 1000) - 1).toInt()
                        var min: Int = second/60
                        var hour: Int = min/60

                        second %= 60
                        min %= 60
                        hour %= 60

                        if(min<10&&second>=10){
                            textView.text = "剩餘時間: 0$hour:0$min:$second"
                        }else if(second<10&&min>=10){
                            textView.text = "剩餘時間: 0$hour:$min:0$second"
                        }else if(min<10&&second<10){
                            textView.text = "剩餘時間: 0$hour:0$min:0$second"
                        }else{
                            textView.text = "剩餘時間: 0$hour:$min:$second"
                        }


                        //當倒數的時間過半時,呼叫函式轉換圖片
                        if(changeimageposition==0&&hour==0&&min==0&&second==12){
                            setimage2()
                        }else if(changeimageposition==1&&hour==0&&min==12&&second==30){
                            setimage2()
                        }else if(changeimageposition==2&&hour==0&&min==25&&second==0){
                            setimage2()
                        }else if(changeimageposition==3&&hour==0&&min==42&&second==30){
                            setimage2()
                        }else if(changeimageposition==4&&hour==0&&min==55&&second==0){
                            setimage2()
                        }
                    }

                    //以下是倒數結束時所要做的動作
                    override fun onFinish() {
                        play.isEnabled=false
                        play.isClickable=false
                        back.isEnabled=true
                        back.isClickable=true
                        setimage()

                        // 如果要跳下一個Activity請寫在這裡

                        // 傳遞辨識工作時間之參數至Countdown Activity

                        //startActivity(intent)

                        var TeleportToInput: Intent

                        if(Times==1) {
                            TeleportToInput =Intent(this@MainActivity, FirstInputInterFace().javaClass)
                            Times++

                            TeleportToInput.putExtra("restNumber", changeimageposition)
                            startActivity(TeleportToInput)
                        }
                        else if(Times==2) {
                            TeleportToInput = Intent(this@MainActivity, SecondInputInterFace().javaClass)
                            Times++
                            TeleportToInput.putExtra("restNumber", changeimageposition)
                            startActivity(TeleportToInput)
                        }
                        else if(Times==3) {
                            TeleportToInput = Intent(this@MainActivity, ThirdInputInterFace().javaClass)
                            TeleportToInput.putExtra("restNumber", changeimageposition)

                            startActivity(TeleportToInput)
                        }

                        textView.text = "FINISH!"
                    }
                }.start()
            }

            //按下重來button後要做的事件處理
            back -> {
                play.isEnabled=true
                play.isClickable = true
                back.isEnabled=false
                back.isClickable = false
                setimagefalse()
                timechoice.isEnabled=true

                //按下重來button時,將進度條歸0
                progressTask.cancel(true)
                progressBar.setProgress(0, true)
                textView.text = backtime
                initProgressTask()
                timer.cancel()
            }
        }
    }

    fun initProgressTask(){
        progressTask = ProgressTask()
        progressTask.progressBar = progressBar
        progressTask.textView = textView
    }
    companion object {
        var Times = 1
    }

}
