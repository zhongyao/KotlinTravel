package com.hongri.kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hongri.kotlin.intent.IntentActivity
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
            val intent = Intent(this, IntentActivity::class.java)
            intent.putExtra("person_data", person)
            startActivity(intent)
        }
    }
}