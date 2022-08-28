package com.hongri.kotlin.chapter11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hongri.kotlin.R
import com.hongri.kotlin.chapter11.retrofit.App
import com.hongri.kotlin.chapter11.retrofit.AppService
import com.hongri.kotlin.chapter11.retrofit.ServiceCreator
import kotlinx.android.synthetic.main.activity_chapter11.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

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