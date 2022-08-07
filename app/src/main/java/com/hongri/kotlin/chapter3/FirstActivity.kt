package com.hongri.kotlin.chapter3

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hongri.kotlin.R
import com.hongri.kotlin.util.Util
import com.hongri.kotlin.util.Util2
import com.hongri.kotlin.util.doSomething
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {
    private val TAG = "FirstActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        /**
         * gradle 中引入的kotlin-android-extensions插件可以使我们省略findViewById()
         */
        btn1.setOnClickListener {
            Toast.makeText(this, "You click button1", Toast.LENGTH_LONG).show()
        }

        btn2.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https:///www.baidu.com")
            startActivity(intent)
        }

        btn3.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }

        btn4.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            val name = "yao"
            intent.putExtra("name", name)
            startActivityForResult(intent, 100)
        }

        btn5.setOnClickListener {
            SecondActivity.actionStart(this, "哈哈", "呵呵")
        }

        /**
         * with函数
         */
        btn6.setOnClickListener {
            val list = listOf("apple", "orange", "banana", "pear", "grape")
            val result = with(StringBuilder()) {
                append("Start eating fruits. \n")
                for (fruit in list) {
                    append(fruit).append("\n")
                }
                append("Ate all fruits.")
                toString()
            }
            Log.d(TAG,  result)
        }

        /**
         * 静态方法调用方式举例
         */
        btn7.setOnClickListener {
            //单例
            Util.doAction()
            //companion object
            Util2.doAction2()
            //顶层方法
            doSomething()
            //Java中需这样调用 顶层方法
//            HelperKt.doSomething()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            100 -> if (resultCode == RESULT_OK) {
                val returnedData = data?.getStringExtra("data_return")
                Toast.makeText(this, returnedData, Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * 创建菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //Kotlin语法，相当于getMenuInflater....
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> Toast.makeText(this, "add", Toast.LENGTH_LONG).show()
            R.id.remove -> Toast.makeText(this, "remove", Toast.LENGTH_LONG).show()
        }
        return true

    }
}