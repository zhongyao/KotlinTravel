package com.hongri.kotlin.chapter3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        /**
         * gradle 中引入的kotlin-android-extensions插件可以使我们省略findViewById()
         */
        btn1.setOnClickListener {
            Toast.makeText(this, "You click button1", Toast.LENGTH_LONG).show()
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