package com.hongri.kotlin.chapter3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val name = intent.getStringExtra("name")
        Toast.makeText(this, name, Toast.LENGTH_LONG).show()

        val param1 = intent.getStringExtra("params1")
        val param2 = intent.getStringExtra("params2")
        Log.d("SecondActivity", "params1: $param1 params2:$param2")

        btn1.setOnClickListener {
            val intent = Intent()
            intent.putExtra("data_return", "hello FirstActivity")
            setResult(RESULT_OK, intent)
            finish()
        }
    }


    /**
     * 这里使用了一个新的语法结构companion object,并在companion object中定义了一个actionStart()方法，之所以要这么写，是因为Kotlin规定，
     * 所有定义在companion object中的方法都可以使用类似于Java静态方法的形式调用。
     */
    companion object {
//        fun actionStart(context: Context, data1: String, data2: String) {
//            val intent = Intent(context, SecondActivity::class.java)
//            intent.putExtra("params1", data1)
//            intent.putExtra("params2", data2)
//            context.startActivity(intent)
//        }
        //上面方法可以用下面代替[apply函数的应用]
        fun actionStart(context: Context, data1: String, data2: String) {
            val intent = Intent(context, SecondActivity::class.java).apply {
                putExtra("params1", data1)
                putExtra("params2", data2)
            }
            context.startActivity(intent)
        }
    }
}