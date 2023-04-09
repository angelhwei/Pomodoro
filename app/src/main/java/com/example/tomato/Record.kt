package com.example.tomato

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tomato.MainActivity
import java.text.DateFormat
import java.util.*

class Record : AppCompatActivity() {
    var List_View: ListView? = null
    var Connection: ArrayAdapter<String>? = null
    var date = Date()
    var longFormat = DateFormat.getDateTimeInstance(
        DateFormat.LONG,
        DateFormat.LONG
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        val button10 = findViewById<View>(R.id.button10) as Button

        button10.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@Record, FirstPage::class.java)
            startActivity(intent)
        }

        supportActionBar!!.hide()

        List_View = findViewById<View>(R.id.List_View) as ListView
        Connection = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Memo
        )
        List_View!!.adapter = Connection
        val IT = intent
        val FirstContent = IT.getStringExtra("內容1")
        val SecondContent = IT.getStringExtra("內容2")
        val ThirdContent = IT.getStringExtra("內容3")

        //測試用
        val it_EveryPage: Intent
        if (IT.getIntExtra("次數3", -1) == 3) Times =
            3 else if (IT.getIntExtra("次數2", -1) == 2) Times =
            2 else if (IT.getIntExtra("次數1", -1) == 1) Times =
            1 else if (IT.getIntExtra("次數0", -1) == 0) Times = 0
        when (Times) {
            1 -> {
                catchHistory()
                var ItemIndex = -1
                var i = 0
                while (i <= 9) {
                    ItemIndex = Detection(Memo[i])
                    if (ItemIndex != -1) break
                    i++
                }
                if (ItemIndex == -1) Toast.makeText(
                    applicationContext,
                    "十個紀錄已經寫滿摟 ! !",
                    Toast.LENGTH_LONG
                ).show() else {
                    Toast.makeText(
                        applicationContext,
                        "第" + (General_Num + 1) + "項紀錄，已經新增第一個活動：" + FirstContent,
                        Toast.LENGTH_LONG
                    ).show()
                    General_Num = ItemIndex
                    //
                    Memo[General_Num] += "\n"
                    Memo[General_Num] += longFormat.format(
                        date
                    )
                    Memo[General_Num] += "\n"

                    Memo[General_Num] += "行程：00：00：25"
                    Memo[General_Num] += "\n"
                    Memo[General_Num] += "------------------------------------------------------------------"
                    Memo[General_Num] += "\n"
                    Memo[General_Num] += "          "
                    Memo[General_Num] += FirstContent
                    //
                }

                //   Use Intent here to call the next page(The Second working operation)
                it_EveryPage = Intent(this,Countdown::class.java)
                var restNumber = intent?.getIntExtra("restNumber", 0)
                it_EveryPage.putExtra("restNumber", restNumber)
                startActivityForResult(it_EveryPage, 1)
                setResult(Activity.RESULT_OK, it_EveryPage)
                finish()
            }
            2 -> {
                Toast.makeText(
                    applicationContext,
                    "第" + (General_Num + 1) + "項紀錄，已經新增第二個活動：" + SecondContent,
                    Toast.LENGTH_LONG
                ).show()
                Memo[General_Num] += "\n"
                Memo[General_Num] += "          "
                Memo[General_Num] += SecondContent
                it_EveryPage = Intent(this, Countdown::class.java)
                var restNumber = intent?.getIntExtra("restNumber", 0)
                it_EveryPage.putExtra("restNumber", restNumber)
                startActivityForResult(it_EveryPage, 1)
                setResult(Activity.RESULT_OK, it_EveryPage)
                finish()
            }
            3 -> {
                Toast.makeText(
                    applicationContext,
                    "第" + (General_Num + 1) + "項紀錄，已經新增第三個活動：" + ThirdContent,
                    Toast.LENGTH_LONG
                ).show()
                Memo[General_Num] += "\n"
                Memo[General_Num] += "          "
                Memo[General_Num] += ThirdContent

                store()
            }

            0 -> {
                catchHistory()
            }

        }
        Connection!!.notifyDataSetChanged()
    }

    fun Detection(s: String): Int {
        return if ("1." == s.trim { it <= ' ' }) 0 else if ("2." == s.trim { it <= ' ' }) 1 else if ("3." == s.trim { it <= ' ' }) 2 else if ("4." == s.trim { it <= ' ' }) 3 else if ("5." == s.trim { it <= ' ' }) 4 else if ("6." == s.trim { it <= ' ' }) 5 else if ("7." == s.trim { it <= ' ' }) 6 else if ("8." == s.trim { it <= ' ' }) 7 else if ("9." == s.trim { it <= ' ' }) 8 else if ("10." == s.trim { it <= ' ' }) 9 else -1
    }

    companion object {
        var Memo = arrayOf(
            "1. ",
            "2. ",
            "3. ",
            "4. ",
            "5. ",
            "6. ",
            "7. ",
            "8. ",
            "9. ",
            "10. "
        )
        var General_Num = 0
        var Times = 0
    }

    fun store(){
        val r0 = getSharedPreferences("r0", Context.MODE_PRIVATE)
        val r1 = getSharedPreferences("r1", Context.MODE_PRIVATE)
        val r2 = getSharedPreferences("r2", Context.MODE_PRIVATE)
        val r3 = getSharedPreferences("r3", Context.MODE_PRIVATE)
        val r4 = getSharedPreferences("r4", Context.MODE_PRIVATE)
        val r5 = getSharedPreferences("r5", Context.MODE_PRIVATE)
        val r6 = getSharedPreferences("r6", Context.MODE_PRIVATE)
        val r7 = getSharedPreferences("r7", Context.MODE_PRIVATE)
        val r8 = getSharedPreferences("r8", Context.MODE_PRIVATE)
        val r9 = getSharedPreferences("r9", Context.MODE_PRIVATE)

        r0.edit()
                .putString("F0", Memo.get(0))
                .apply()
        r1.edit()
                .putString("F1", Memo.get(1))
                .apply()
        r2.edit()
                .putString("F2", Memo.get(2))
                .apply()
        r3.edit()
                .putString("F3", Memo.get(3))
                .apply()
        r4.edit()
                .putString("F4", Memo.get(4))
                .apply()
        r5.edit()
                .putString("F5", Memo.get(5))
                .apply()
        r6.edit()
                .putString("F6", Memo.get(6))
                .apply()
        r7.edit()
                .putString("F7", Memo.get(7))
                .apply()
        r8.edit()
                .putString("F8", Memo.get(8))
                .apply()
        r9.edit()
                .putString("F9", Memo.get(9))
                .apply()
    }

    fun catchHistory(){
        val Get0 = getSharedPreferences("r0", Context.MODE_PRIVATE).getString("F0", "1. ")
        val Get1 = getSharedPreferences("r1", Context.MODE_PRIVATE).getString("F1", "2. ")
        val Get2 = getSharedPreferences("r2", Context.MODE_PRIVATE).getString("F2", "3. ")
        val Get3 = getSharedPreferences("r3", Context.MODE_PRIVATE).getString("F3", "4. ")
        val Get4 = getSharedPreferences("r4", Context.MODE_PRIVATE).getString("F4", "5. ")
        val Get5 = getSharedPreferences("r5", Context.MODE_PRIVATE).getString("F5", "6. ")
        val Get6 = getSharedPreferences("r6", Context.MODE_PRIVATE).getString("F6", "7. ")
        val Get7 = getSharedPreferences("r7", Context.MODE_PRIVATE).getString("F7", "8. ")
        val Get8 = getSharedPreferences("r8", Context.MODE_PRIVATE).getString("F8", "9. ")
        val Get9 = getSharedPreferences("r9", Context.MODE_PRIVATE).getString("F9", "10. ")

        if (Get0 != null) {
            Memo[0] = Get0
        }
        if (Get1 != null) {
            Memo[1] = Get1
        }
        if (Get2 != null) {
            Memo[2] = Get2
        }
        if (Get3 != null) {
            Memo[3] = Get3
        }
        if (Get4 != null) {
            Memo[4] = Get4
        }
        if (Get5 != null) {
            Memo[5] = Get5
        }
        if (Get6 != null) {
            Memo[6] = Get6
        }
        if (Get7 != null) {
            Memo[7] = Get7
        }
        if (Get8 != null) {
            Memo[8] = Get8
        }
        if (Get9 != null) {
            Memo[9] = Get9
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun clearForTest(){
        val Get0 = getSharedPreferences("r0", Context.MODE_PRIVATE)
        val Get1 = getSharedPreferences("r1", Context.MODE_PRIVATE)
        val Get2 = getSharedPreferences("r2", Context.MODE_PRIVATE)
        val Get3 = getSharedPreferences("r3", Context.MODE_PRIVATE)
        val Get4 = getSharedPreferences("r4", Context.MODE_PRIVATE)
        val Get5 = getSharedPreferences("r5", Context.MODE_PRIVATE)
        val Get6 = getSharedPreferences("r6", Context.MODE_PRIVATE)
        val Get7 = getSharedPreferences("r7", Context.MODE_PRIVATE)
        val Get8 = getSharedPreferences("r8", Context.MODE_PRIVATE)
        val Get9 = getSharedPreferences("r9", Context.MODE_PRIVATE)

        Get0.edit().clear()
        Get1.edit().clear()
        Get2.edit().clear()
        Get3.edit().clear()
        Get4.edit().clear()
        Get5.edit().clear()
        Get6.edit().clear()
        Get7.edit().clear()
        Get8.edit().clear()
        Get9.edit().clear()

        Get0.edit().apply()
        Get1.edit().apply()
        Get2.edit().apply()
        Get3.edit().apply()
        Get4.edit().apply()
        Get5.edit().apply()
        Get6.edit().apply()
        Get7.edit().apply()
        Get8.edit().apply()
        Get9.edit().apply()
    }


}