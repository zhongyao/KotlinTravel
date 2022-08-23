package com.hongri.kotlin.chapter10

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter10.*
import kotlin.concurrent.thread

class Chapter10Activity : AppCompatActivity() {

    private val updateText = 1;
    private val tag = "Chapter10Activity";
    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                updateText -> tvContent.text = "Nice to meet you."
            }
        }
    }

    lateinit var downloadBinder: MyService.DownloadBinder
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(tag, "onServiceConnected")
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(tag, "onServiceDisconnected")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter10)

        threadBtn.setOnClickListener {
//            Thread {
//                //编写具体的逻辑
//            }.start()

            /**
             * Kotlin提供的更加简便的开启线程的方式
             */
            thread {
                //编写具体的逻辑
                val msg = Message()
                msg.what = updateText
                handler.sendMessage(msg)
            }
        }

        startServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        stopServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            stopService(intent)
        }

        bindServiceBtn.setOnClickListener {
            val intent = Intent(this, MyService::class.java)
            this.bindService(intent, connection, BIND_AUTO_CREATE)
        }

        unBindServiceBtn.setOnClickListener {
            unbindService(connection)
        }

    }
}