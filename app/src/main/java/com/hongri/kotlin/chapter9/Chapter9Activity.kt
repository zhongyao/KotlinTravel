package com.hongri.kotlin.chapter9

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter9.*

class Chapter9Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter9)

        notificationBtn1.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                val channel = NotificationChannel(NORMAL_CHANNEL_ID, "普通渠道", NotificationManager.IMPORTANCE_DEFAULT)
                manager.createNotificationChannel(channel)

                val intent = Intent(this, NotificationActivity::class.java)
                val pi = PendingIntent.getActivity(this, 0, intent, 0)

                val notification = NotificationCompat.Builder(this, NORMAL_CHANNEL_ID)
                    .setContentTitle("title")
                    .setContentText("contentText")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
                    .setContentIntent(pi)
                        //自动消失[1]
                    .setAutoCancel(true)
                    .build()
                manager.notify(1, notification)

                //自动小时[2]
//                manager.cancel(1)
            }
        }
    }

    companion object {
        private const val NORMAL_CHANNEL_ID = "100"
    }
}