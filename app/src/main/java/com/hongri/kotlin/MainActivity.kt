package com.hongri.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hongri.kotlin.intent.Book
import com.hongri.kotlin.intent.IntentActivity
import com.hongri.kotlin.intent.Movie
import com.hongri.kotlin.intent.Person

class MainActivity : AppCompatActivity() {

    lateinit var intentBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        intentBtn = findViewById(R.id.intentBtn)
        intentBtn.setOnClickListener {

            val person = Person()
            person.name = "Jack"
            person.age = 18

            val book = Book()
            book.bookName = "平凡的世界"
            book.pages = 788

            val movie = Movie("肖申克的救赎", 1994)

            val intent = Intent(this, IntentActivity::class.java)
            intent.putExtra("person_data", person)
            intent.putExtra("book_data", book)
            intent.putExtra("movie_data", movie)
            startActivity(intent)
        }
    }
}