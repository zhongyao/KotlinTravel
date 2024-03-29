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
import com.hongri.kotlin.MainActivity
import com.hongri.kotlin.R
import com.hongri.kotlin.chapter10.covariation.Person
import com.hongri.kotlin.chapter10.covariation.SimpleData
import com.hongri.kotlin.chapter10.covariation.Student
import com.hongri.kotlin.chapter10.covariation.Transformer
import com.hongri.kotlin.chapter10.genericity.startActivity
import com.hongri.kotlin.util.getThreadName
import kotlinx.android.synthetic.main.activity_chapter10.*
import kotlin.concurrent.thread

class Chapter10Activity : AppCompatActivity() {

    private val updateText = 1;

    /**
     * 定义常量TAG
     */
    companion object {
        private const val TAG = "Chapter10Activity";
    }

    private val handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                updateText -> tvContent.text = "Nice to meet you."
            }
        }
    }

    lateinit var downloadBinder: MyService.DownloadBinder

    //此处object为实现一个接口/类
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected")
            downloadBinder = service as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProgress()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected")
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

        foregroundServiceBtn.setOnClickListener {
            val intent = Intent(this, MyForegroundService::class.java)
            startService(intent)
        }

        intentServiceBtn.setOnClickListener {
            Log.d("MyIntentService", "-" + getThreadName())
            val intent = Intent(this, MyIntentService::class.java)
            startService(intent)
        }

        reifiedBtn.setOnClickListener {
            startActivity<MainActivity>(this) {
                putExtra("name", "yao")
                putExtra("age", 18)
            }
        }

        covariationBtn.setOnClickListener {
            val student = Student("yao", 18)

            /**
             * 这里SimpleData类已经进行了协变声明，
             * 那么SimpleData<Student> 自然就是SimpleData<Person>的子类了
             * 所以这里可以安全地向handleMyData()方法中传递参数。
             */
            val data = SimpleData<Student>(student)
            handleMyData(data)
            val studentData = data.get()
        }

        inversionBtn.setOnClickListener {
            //这里我们在泛型T的声明前面加上了一个关键字in，这就意味着现在T只能出现在in位置上，
            // 而不能出现在out位置上，同时也意味着Transformer在泛型T上是可逆的。
            val trans = object : Transformer<Person> {
                override fun transform(t: Person): String {
                    return "${t.name} ${t.age}"
                }
            }
            //可以正常编译，因为此时Transformer<Person> 已经成为了Transformer<Student>的子类型
            handleTransformer(trans)
        }

    }

    private fun handleTransformer(trans: Transformer<Student>) {
        val student = Student("Tom", 19)
        val result = trans.transform(student)
    }

    private fun handleMyData(data: SimpleData<Person>) {
        val personData = data.get()
    }
}