package com.hongri.kotlin.chapter4.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.hongri.kotlin.R
import com.hongri.kotlin.chapter4.recycler.bean.Fruit
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {
    private val fruitList = ArrayList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initFruits()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = FruitAdapter(fruitList)
    }

    private fun initFruits() {
        fruitList.add(Fruit("Apple", "China"))
        fruitList.add(Fruit("Apple1", "China"))
        fruitList.add(Fruit("Apple2", "China"))
        fruitList.add(Fruit("Apple3", "China"))
        fruitList.add(Fruit("Apple4", "China"))
        fruitList.add(Fruit("Apple5", "China"))
        fruitList.add(Fruit("Apple6", "China"))
        fruitList.add(Fruit("Banana", "Thailand"))
        fruitList.add(Fruit("Banana1", "Thailand"))
        fruitList.add(Fruit("Banana2", "Thailand"))
        fruitList.add(Fruit("Banana3", "Thailand"))
        fruitList.add(Fruit("Banana4", "Thailand"))
        fruitList.add(Fruit("Banana5", "Thailand"))
        fruitList.add(Fruit("Banana6", "Thailand"))
        fruitList.add(Fruit("Banana7", "Thailand"))
        fruitList.add(Fruit("Banana8", "Thailand"))
        fruitList.add(Fruit("Banana9", "Thailand"))
        fruitList.add(Fruit("Banana10", "Thailand"))
    }
}