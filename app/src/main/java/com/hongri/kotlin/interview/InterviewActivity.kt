package com.hongri.kotlin.interview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hongri.kotlin.R
import com.hongri.kotlin.util.JavaUtil
import com.hongri.kotlin.util.Util
import com.hongri.kotlin.util.Util2

class InterviewActivity : AppCompatActivity() {
    private val TAG = "InterviewActivity";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interview)


        /**
         * Kotlin 中注解 @JvmOverloads 的作用
         */
//        Util.f("first param")
//        Util.f("first param", 100)
//        Util.f("first param", 1, "third param")

        JavaUtil.testJvmOverloads()

        Util.fList()
    }
}