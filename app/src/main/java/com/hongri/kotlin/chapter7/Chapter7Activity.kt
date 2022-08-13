package com.hongri.kotlin.chapter7

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter7.*
import java.io.*

/**
 * 数据存储Activity
 */
class Chapter7Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter7)

        saveFileBtn.setOnClickListener {
            try {
                val output = openFileOutput("data", MODE_PRIVATE)
                val writer = BufferedWriter(OutputStreamWriter(output))
                /**
                 * use：
                 * Kotlin提供的内置扩展函数，它会保证在Lambda表达式中的所有代码执行完成后
                 * 自动将外层的流关闭
                 */
                writer.use {
                    it.write("哈哈哈呵呵呵")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        restoreFileBtn.setOnClickListener {
            val content = StringBuilder()
            try {
                val input = openFileInput("data")
                val reader = BufferedReader(InputStreamReader(input))
                reader.use {
                    /**
                     * forEachLine函数：Kotlin提供的一个内置扩展函数，它会将读到的每行内容都回调到Lambda表达式中，
                     * 我们在Lambda表达式中完成拼接逻辑即可。
                     */
                    reader.forEachLine {
                        content.append(it)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            Toast.makeText(this, "内容：$content", Toast.LENGTH_LONG).show()
        }

        saveSpBtn.setOnClickListener {
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putString("name", "yao")
            editor.putString("gender", "male")
            editor.putBoolean("goodman", true)
            editor.apply()
        }

        restoreSpBtn.setOnClickListener {
            val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
            val name = prefs.getString("name", "")
            val gender = prefs.getString("gender", "")
            val goodman = prefs.getBoolean("goodman", false)
            Toast.makeText(this, "name:$name gender:$gender goodman:$goodman", Toast.LENGTH_LONG)
                .show()
        }
    }
}