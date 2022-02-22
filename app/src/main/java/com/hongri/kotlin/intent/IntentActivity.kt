package com.hongri.kotlin.intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hongri.kotlin.R

class IntentActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "IntentActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        val person = intent.getSerializableExtra("person_data") as Person
        Log.d(Companion.TAG, "person name is ${person.name}, age is ${person.age}")
    }

}