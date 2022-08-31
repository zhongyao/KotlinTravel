package com.hongri.kotlin.chapter12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter12.*
import com.hongri.kotlin.util.max

class Chapter12Activity : AppCompatActivity() {

    companion object {
        private const val TAG = "Chapter12Activity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter12)

        maxNumBtn.setOnClickListener {
           val result = max(2.0, 3.0, 4.0, 3.3)
            Log.d(TAG, "result:$result");
        }
    }
}