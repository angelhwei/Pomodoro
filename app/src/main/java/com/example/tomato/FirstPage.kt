package com.example.tomato

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class
FirstPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_page)

        supportActionBar!!.hide()

        val button1 = findViewById<View>(R.id.button1) as Button

        button1.setOnClickListener {
            val intent = Intent()
            intent.setClass(this@FirstPage, MainActivity::class.java)
            startActivity(intent)
        }

        val button2 = findViewById<View>(R.id.button2) as Button

        button2.setOnClickListener {
            val it_Record = Intent(this, Record::class.java)
            it_Record.putExtra("次數0", 0)
            setResult(Activity.RESULT_OK, it_Record)
            startActivityForResult(it_Record, 1)
            finish()
        }
    }

}

