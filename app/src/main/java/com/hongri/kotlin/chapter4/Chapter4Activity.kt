package com.hongri.kotlin.chapter4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter4.*
const val name: String = "yaoge"
class Chapter4Activity : AppCompatActivity(), View.OnClickListener {
    private val instance by lazy { this }//这里使用了委托，表示只有使用到instance才会执行该段代码
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter4)
        btnAlertDialog.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnAlertDialog -> {
                AlertDialog.Builder(this).apply {
                    setTitle("This is Dialog")
                    setMessage("Something important.")
                    setCancelable(false)
                    setPositiveButton("OK") { dialog, which ->
                        Toast.makeText(this@Chapter4Activity, "点击了OK", Toast.LENGTH_LONG).show()
                    }

                    setNegativeButton("Cancel") { dialog, which ->
                        Toast.makeText(instance, "点击了Cancel", Toast.LENGTH_LONG).show()
                    }
                    show()
                }
            }
        }
    }
}