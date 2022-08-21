package com.hongri.kotlin.chapter9

import android.app.Notification
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

        notificationBtn2.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                val channel = NotificationChannel(NORMAL_RICH_TEXT_CHANNEL_ID, "富文本渠道", NotificationManager.IMPORTANCE_DEFAULT)
                manager.createNotificationChannel(channel)

                val intent = Intent(this, NotificationActivity::class.java)
                val pi = PendingIntent.getActivity(this, 2, intent, 0)

                val notification = NotificationCompat.Builder(this, NORMAL_RICH_TEXT_CHANNEL_ID)
                    .setContentTitle("title")
                        //文本
//                    .setStyle(NotificationCompat.BigTextStyle().bigText("Kotlin中的注释几乎和Java没什么区别。唯一的区别在于Kotlin中的多行注释中可以嵌套多行注释，而Java中是不能的。"))
                        //图片
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setStyle(NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(resources, R.drawable.bird)))
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bird))
                    .setContentIntent(pi)
                    //自动消失[1]
                    .setAutoCancel(true)
                    .build()
                manager.notify(2, notification)
            }
        }

        /**
         * 开发者只能在创建渠道通知的时候为它指定初始的重要等级，如果用户不认可这个重要等级的话，可以随时进行修改，
         * 开发者对此无权进行调整和变更，因为通知渠道一旦创建就不能再通过代码修改了。
         */
        notificationBtn3.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

                val channel = NotificationChannel(IMPORTANT_CHANNEL_ID, "重要渠道", NotificationManager.IMPORTANCE_HIGH)
                manager.createNotificationChannel(channel)

                val intent = Intent(this, NotificationActivity::class.java)
                val pi = PendingIntent.getActivity(this, 0, intent, 0)

                val notification = NotificationCompat.Builder(this, IMPORTANT_CHANNEL_ID)
                    .setContentTitle("important notification")
                    .setContentText("contentText")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bird))
                    .setContentIntent(pi)
                    //自动消失[1]
                    .setAutoCancel(true)
                    .build()
                manager.notify(3, notification)
            }
        }
    }

    companion object {
        private const val NORMAL_CHANNEL_ID = "100"
        private const val NORMAL_RICH_TEXT_CHANNEL_ID = "101"
        private const val IMPORTANT_CHANNEL_ID = "102"
    }
}