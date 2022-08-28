package com.hongri.kotlin.chapter11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter11.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class Chapter11Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter11)

        requestBtn.setOnClickListener {
            sendRequest()
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