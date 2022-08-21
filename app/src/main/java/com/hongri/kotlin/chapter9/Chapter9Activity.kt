package com.hongri.kotlin.chapter9

import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter9.*
import java.io.File

class Chapter9Activity : AppCompatActivity() {

    private val takePhoto = 1
    private val fromAlbum = 2
    lateinit var imageUri: Uri
    private lateinit var outputImage: File

    companion object {
        private const val NORMAL_CHANNEL_ID = "100"
        private const val NORMAL_RICH_TEXT_CHANNEL_ID = "101"
        private const val IMPORTANT_CHANNEL_ID = "102"
    }

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

        cameraBtn.setOnClickListener {
            //应用关联缓存目录【不用申请权限】/sdcard/Android/data/<package name>/cache
            outputImage = File(externalCacheDir, "output_image.jpg")
            if (outputImage.exists()) {
                outputImage.delete()
            }
            outputImage.createNewFile()
            imageUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //从Android7.0系统开始，直接使用本地真实路径的Uri被认为是不安全的，会抛出一个FileUriExposedException异常
                FileProvider.getUriForFile(this, "com.example.cameraalbumtest.fileprovider", outputImage)
            } else {
                Uri.fromFile(outputImage)
            }
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, takePhoto)
        }

        galleryBtn.setOnClickListener {
            //打开文件选择器
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            //指定只显示图片
            intent.type = "image/*"
            startActivityForResult(intent, fromAlbum)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            takePhoto -> {
                if (resultCode == Activity.RESULT_OK) {
                    val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri))
                    iv.setImageBitmap(rotateIfRequired(bitmap))
                }
            }
            fromAlbum -> {
                if (resultCode == RESULT_OK) {
                    data?.data?.let { uri ->
                        val bitmap = getBitmapFromUri(uri)
                        iv.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }

    private fun getBitmapFromUri(uri: Uri) = contentResolver.openFileDescriptor(uri, "r")?.use {
        BitmapFactory.decodeFileDescriptor(it.fileDescriptor)
    }

    private fun rotateIfRequired(bitmap: Bitmap): Bitmap {
        val exif = ExifInterface(outputImage.path)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when(orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateBitmap(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateBitmap(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateBitmap(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateBitmap(bitmap: Bitmap, degree: Int): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.width, bitmap.height, matrix, true)
        bitmap.recycle()
        return rotatedBitmap
    }

}