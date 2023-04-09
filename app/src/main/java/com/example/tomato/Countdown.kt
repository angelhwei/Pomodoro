package com.example.tomato


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_countdown.*
import kotlinx.android.synthetic.main.activity_main.back
import kotlinx.android.synthetic.main.activity_main.play
import kotlinx.android.synthetic.main.activity_main.textView
import pl.droidsonroids.gif.GifImageView


class Countdown : AppCompatActivity() {

    lateinit var gifimageview: GifImageView
    var temptime: Long = 0  // 倒數參數


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countdown)

        supportActionBar!!.hide()

        this.play.isEnabled = true
        this.play.isClickable = true
        this.back.isEnabled = false
        this.back.isClickable = false
        this.timechoice.visibility = View.GONE  // 將下拉選單移除
        this.play.setOnClickListener(mOnClickListener)  // 監聽開始按鍵
        this.back.setOnClickListener(mOnClickListener)  // 監聽重來按鍵

        gifimageview=findViewById(R.id.gifImageView)

        // restNumber為接收前一個Activity傳入的參數(番茄鐘執行時間)，以得知要休息幾分鐘
        // 工作50秒;休息10秒鐘(DEMO用)
        // 工作25分;休息5分鐘
        // 工作50分;休息10分鐘
        // 工作1時25分;休息15分鐘
        // 工作1時50分;休息20分鐘
        var restNumber = intent?.getIntExtra("restNumber", 0)
        var Countdown_Text: TextView
        var starttime: TextView
        val builder = AlertDialog.Builder(this)  // 告示框

        Countdown_Text = findViewById<TextView>(R.id.textView)
        starttime = findViewById<TextView>(R.id.starttime)

        starttime.textSize = 30f
        builder.setTitle("休息時間到囉~~")
        builder.setPositiveButton("OK", null)

        // 工作時間不同，會有不同的休息時間
        if (restNumber == 0) {  // Demo用
            temptime = 5000
            builder.setMessage("休息5秒鐘~")
            starttime.setText("休息5秒鐘~~")
            Countdown_Text.setText("休息時間: 00:00:05")
        }
        else if (restNumber == 1) {  // 工作25分
            builder.setMessage("休息5分鐘")
            starttime.setText("休息5分鐘~~")
            Countdown_Text.setText("休息時間: 00:05:00")
            temptime = 300000
        }
        else if (restNumber == 2) {  // 工作50分
            builder.setMessage("休息10分鐘")
            starttime.setText("休息10分鐘~~")
            Countdown_Text.setText("休息時間: 00:10:00")
            temptime = 600000
        }
        else if (restNumber == 3) {  // 工作1時25分
            builder.setMessage("休息15分鐘")
            starttime.setText("休息15分鐘~~")
            Countdown_Text.setText("休息時間: 00:15:00")
            temptime = 300000
        }
        else if (restNumber == 4) {  // 工作1時50分
            builder.setMessage("休息20分鐘")
            starttime.setText("休息20分鐘~~")
            Countdown_Text.setText("休息時間: 00:20:00")
            temptime = 600000
        }
        // 顯示告示框
        val alert = builder.create()
        alert.show()
    }

    fun setimage1(){
        Glide.with(this)
            .load(R.drawable.zzz1)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(gifimageview)
    }

    //以下是用來監聽按下button後,進度條同時開始運作,顯示倒數的數字
    val mOnClickListener = View.OnClickListener { it ->
        when (it) {
            //按下開始button後要做的事件處理
            play -> {
                play.isEnabled = false
                play.isClickable = false
                back.isEnabled = false
                back.isClickable = false
                setimage1()

                val timer = object : CountDownTimer(
                    temptime + 10 + 1000, 1000) {

                    override fun onTick(millisUntilFinished: Long) {
                        var second: Int =
                            (Math.round(millisUntilFinished.toDouble() / 1000) - 1).toInt()
                        var min: Int = second / 60
                        var hour: Int = min / 60

                        second = second % 60
                        min = min % 60
                        hour = hour % 60

                        if (min < 10 && second >= 10) {
                            textView.setText("休息倒數: 0" + hour + ":0" + min + ":" + second)
                        } else if (second < 10 && min >= 10) {
                            textView.setText("休息倒數: 0" + hour + ":" + min + ":0" + second)
                        } else if (min < 10 && second < 10) {
                            textView.setText("休息倒數: 0" + hour + ":0" + min + ":0" + second)
                        } else {
                            textView.setText("休息倒數: 0" + hour + ":" + min + ":" + second)
                        }
                    }

                    //以下是倒數結束時所要做的動作
                    override fun onFinish() {
                        // 如果要跳下一個Activity請寫在這裡
                        textView.setText("FINISH!")
                        val TeleportToMainActivity = Intent(this@Countdown,
                                MainActivity().javaClass)
                        startActivityForResult(TeleportToMainActivity,1)
                    }
                }
                timer.start()  // 執行倒數功能
            }
        }
    }
}
