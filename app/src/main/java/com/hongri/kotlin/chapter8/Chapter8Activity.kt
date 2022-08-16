package com.hongri.kotlin.chapter8

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hongri.kotlin.R
import com.hongri.kotlin.chapter8.genericity.MineClass
import com.hongri.kotlin.chapter8.genericity.MyClass
import kotlinx.android.synthetic.main.activity_chapter8.*

class Chapter8Activity : AppCompatActivity() {

    private val contactsList = ArrayList<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var TAG = "Chapter8Activity"
    private var value = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter8)

        ifBtn.setOnClickListener {
            Log.d(TAG, "begin")
            val cur = 100
            value = if (this.value == 10) {
                Log.d(TAG, "if...")
                //满足if条件赋值cur给value
                cur
            } else {
                if (true) {
                    //直接return 后面代码不再执行
                    return@setOnClickListener
                }
                Log.d(TAG, "else...")
                //满足else条件赋值cur*2给value
                cur * 2
            }

            Log.d(TAG, "end -- value:$value")
        }

        classByBtn.setOnClickListener {
            //类委托
        }

        paramByBtn.setOnClickListener {
            //委托属性
        }

        initRv()
    }

    private fun initRv() {
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contactsList)
        lv.adapter = adapter
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                1
            )
        } else {
            readContacts()
        }
    }

    private fun readContacts() {
        //查询联系人数据
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )?.apply {
            while (moveToNext()) {
                //获取联系人姓名
                val displayName =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number =
                    getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contactsList.add("$displayName\n$number")
            }
            adapter.notifyDataSetChanged()
            close()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readContacts()
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    /**
     * 泛型调用
     */
    private fun generic() {
        //泛型类调用
        val myClass = MyClass<Int>()
        val result = myClass.method(323)

        //泛型方法调用
        val mineClass = MineClass()
//        val res = mineClass.method<Int>(323)
        /**
         * Kotlin拥有非常出色的类型推导机制，例如我们传入了一个Int类型的参数，它能够自动推导出
         * 泛型的类型就是Int类型，因此这里可以直接省略泛型的指定。
         */
        val res = mineClass.method(323)
    }
}