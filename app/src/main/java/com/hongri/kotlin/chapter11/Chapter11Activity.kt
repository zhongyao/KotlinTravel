package com.hongri.kotlin.chapter11

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hongri.kotlin.R
import com.hongri.kotlin.chapter11.retrofit.App
import com.hongri.kotlin.chapter11.retrofit.AppService
import com.hongri.kotlin.chapter11.retrofit.ServiceCreator
import kotlinx.android.synthetic.main.activity_chapter11.*
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class Chapter11Activity : AppCompatActivity() {

    companion object {
        private const val TAG = "Chapter11Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter11)

        requestBtn.setOnClickListener {
//            sendRequest()
            sendRequestWithOkHttp()
        }

        retrofitBtn.setOnClickListener {
//            val appService = ServiceCreator.create(AppService::class.java)
            val appService = ServiceCreator.create<AppService>()
            appService.getData(2).enqueue(object : Callback<App> {
                override fun onResponse(call: Call<App>, response: Response<App>) {
                    val result = response.body()
                    Log.d(TAG, "result:$result")
                }

                override fun onFailure(call: Call<App>, t: Throwable) {
                    Log.d(TAG, "onFailure")
                }

            })
        }

        coroutinesBtn.setOnClickListener {
            /**
             * runBlocking函数同样会创建一个协程的作用域，但是它可以
             * 保证在协程作用域内的所有代码和子协程没有全部执行完之前一直阻塞当前线程。
             * 【需要注意的是runBlocking容易产生一些性能上的问题】
             */
//            runBlocking {
//                Log.d(TAG, "codes run in coroutine scope")
//                delay(1500)
//                Log.d(TAG, "codes run in coroutine scope finished")
//            }

            /**
             * 使用launch函数创建多个协程
             * 子协程的特点是如果外层作用域的协程结束了，那么该作用域下的所有子协程都会结束。
             */
/*            runBlocking {
                launch {
                    Log.d(TAG, "launch-1")
                    delay(1000)
                    Log.d(TAG, "launch-1 finished")
                }

                launch {
                    Log.d(TAG, "launch-2")
                    delay(1000)
                    Log.d(TAG, "launch-2 finished")
                }
            }*/

            /**
             * 验证协程的高效率
             */
//            val start = System.currentTimeMillis()
//            runBlocking {
//                repeat(100000) {
//                    launch {
//                        Log.d(TAG, ".")
//                    }
//                }
//            }
//            val end = System.currentTimeMillis()
//            Log.d(TAG, ((end - start).toString()))

            /**
             * 调用挂起函数
             */
//            val start = System.currentTimeMillis()
//            runBlocking {
//                repeat(100000) {
//                    printDot()
//                }
//            }
//            val end = System.currentTimeMillis()
//            Log.d(TAG, ((end - start).toString()))

            /**
             * 创建一个协程并获取它的执行结果
             */
//            runBlocking {
//                val result = async {
//                    5 + 5
//                }.await()
//                Log.d(TAG, "result:$result")
//            }
            //或
            /**
             * 线程参数
             * Dispatchers.Default：CPU密集型【低并发】
             * Dispatchers.IO：IO密集型 【高并发】
             * Dispatchers.Main：不会开启子线程 【主线程中执行代码】
             */
            runBlocking {
                val result =
                    withContext(Dispatchers.Default) {
                        5 + 5
                    }
                Log.d(TAG, "result:$result")
            }

            /**
             * 两个async函数完全可以同时执行从而提高运行效率
             */
            runBlocking {
                val start = System.currentTimeMillis()
                val deferred1 = async {
                    delay(1000)
                    5 + 5
                }

                val deferred2 = async {
                    delay(1000)
                    4 + 6
                }
                Log.d(TAG, "result is ${deferred1.await() + deferred2.await()}")
                val end = System.currentTimeMillis()
                Log.d(TAG, "cost ${end - start}milliseconds")
            }

            /**
             * 使用协程简化回调的写法
             */
            val appService = ServiceCreator.create<AppService>()
            appService.getAppData().enqueue(object : Callback<App> {
                override fun onResponse(call: Call<App>, response: Response<App>) {
                    //得到服务器数据
                }

                override fun onFailure(call: Call<App>, t: Throwable) {
                    //异常处理
                }
            })

            //上面可优化为：
            suspend fun getAppData() {
                try {
                    val app = ServiceCreator.create<AppService>().getAppData().await()
                    //对服务器相应的数据做处理
                } catch (e: Exception) {
                    //对异常情况做处理
                }
            }
        }
    }

    /**
     * 通用方法【可提到通用工具类中】：
     * 不管以后我们要发起多少次网络请求，都不用再重复进行回调实现了【只写一次即可】
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }

    /**
     * 调用挂起函数
     * coroutineScope跟runBlocking函数区别：
     * 1、coroutineScope只会阻塞当前协程，既不影响其他协程，也不影响任何线程，因此不会造成性能上的问题
     * 2、runBlocking由于会阻塞当前线程，如果再主线程中调用的话，可能会造成页面卡死。
     */
    private suspend fun printDot() = coroutineScope {
        launch {
            Log.d(TAG, ".")
            //挂起函数
            delay(1000)
        }
    }

    private fun sendRequestWithOkHttp() {
        thread {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("https://www.baidu.com")
                    .build()
                val response = client.newCall(request).execute()
                val responseData = response.body?.string()
                if (responseData != null) {
                    showResponse(responseData)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun sendRequest() {
        var connection: HttpURLConnection? = null
        //开启子线程
        thread {
            try {
                val stringBuilder = StringBuilder()
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                connection?.connectTimeout = 8000
                connection?.readTimeout = 8000
                val input = connection?.inputStream

                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    reader.forEachLine {
                        stringBuilder.append(it)
                    }
                }
                showResponse(stringBuilder.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                connection?.disconnect()
            }
        }
    }

    private fun showResponse(response: String) {
        runOnUiThread {
            tv.text = response
        }
    }
}