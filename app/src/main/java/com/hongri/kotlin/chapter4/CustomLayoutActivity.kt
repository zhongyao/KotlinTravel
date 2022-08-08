package com.hongri.kotlin.chapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hongri.kotlin.R

class CustomLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_layout)
        supportActionBar?.hide()
    }
}