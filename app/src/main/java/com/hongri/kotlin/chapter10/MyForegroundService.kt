package com.hongri.kotlin.chapter10

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.hongri.kotlin.MainActivity
import com.hongri.kotlin.R

/**
 * 前台服务
 */
class MyForegroundService : Service() {

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "200";
        private const val NOTIFICATION_CHANNEL_NAME = "foregroundService"
    }

    override fun onCreate() {
        super.onCreate()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("This is content title")
            .setContentText("This is content text")
            .setContentIntent(pi)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.bird))
            .build()
        /**
         * 1、调用该方法后就会让 MyForegroundService 变成一个前台Service，并在系统状态栏显示出来。
         * 2、从Android9.0开始，使用前台Service必须在AndroidManifest.xml文件中生命权限
         */
        startForeground(2, notification)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}