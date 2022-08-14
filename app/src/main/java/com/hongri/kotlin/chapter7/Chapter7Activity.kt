package com.hongri.kotlin.chapter7

import android.content.ContentValues
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hongri.kotlin.R
import kotlinx.android.synthetic.main.activity_chapter7.*
import java.io.*
import java.lang.NullPointerException

/**
 * 数据存储Activity
 */
class Chapter7Activity : AppCompatActivity() {
    private var TAG = "Chapter7Activity"
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

        createSQLBtn.setOnClickListener {
            val dbHelper = MyDatabaseHelper(this, "BookStore.db", 1)
            dbHelper.writableDatabase
        }

        upgradeSQLBtn.setOnClickListener {
            val dbHelper = MyDatabaseHelper(this, "BookStore.db", 3)
            dbHelper.writableDatabase
        }

        val dbHelper = MyDatabaseHelper(this, "BookStore.db", 3)
        insertSQLBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            val value1 = ContentValues().apply {
                put("name", "yao book")
                put("author", "yao")
                put("pages", 454)
                put("price", 16.98)
            }
            db.insert("Book", null, value1)

            val value2 = ContentValues().apply {
                put("name", "hongri book")
                put("author", "hongri")
                put("pages", 510)
                put("price", 19.93)
            }
            db.insert("Book", null, value2)

            //SQL插入数据
//            db.execSQL(
//                "insert into Book(name, author, pages, price) values(?, ? ,?, ?)",
//                arrayOf("hongri book", "hongri", 510, 19.93)
//            )
        }

        updateSQLBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put("price", 10.99)
            db.update("Book", values, "name = ?", arrayOf("yao book"))

            //SQL更新数据
            db.execSQL("update Book set price = ? where name = ?", arrayOf("10.99", "yao book"))
        }

        deleteSQLBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("Book", "pages > ?", arrayOf("500"))

            //SQL删除数据
//            db.execSQL("delete from Book where pages > ?", arrayOf("500"))
        }

        selectSQLBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            //查询表中所有数据
            val cursor = db.query("Book", null, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    //遍历cursor对象，取出数据并打印
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getString(cursor.getColumnIndex("pages"))
                    val price = cursor.getString(cursor.getColumnIndex("price"))
                    Log.d(TAG, "book name is $name")
                    Log.d(TAG, "book author is $author")
                    Log.d(TAG, "book pages is $pages")
                    Log.d(TAG, "book price is $price")
                } while (cursor.moveToNext())
            }
            cursor.close()

            //SQL查询数据
//            val cursor = db.rawQuery("select * from Book", null)
        }

        transactionBtn.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.beginTransaction()
            try {
                db.delete("Book", null, null)
                val values = ContentValues().apply {
                    put("name", "yaoyao book")
                    put("author", "yaoyao")
                    put("pages", 10002)
                    put("price", 203)
                }
                db.insert("Book", null, values)
                db.setTransactionSuccessful()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                db.endTransaction()
            }
        }

        upgradeBetterBtn.setOnClickListener {
            //升级到第3版本时这样写
            val db = MyDatabaseHelper(this, "BookStore.db", 3)
            db.writableDatabase
        }
    }
}