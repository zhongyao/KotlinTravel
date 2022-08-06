package com.hongri.kotlin.chapter3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val name = intent.getStringExtra("name")
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        btn1.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data_return", "hello FirstActivity")
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}