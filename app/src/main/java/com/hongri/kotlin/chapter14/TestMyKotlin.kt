package com.hongri.kotlin.chapter14

import java.util.*

/**
 * @author：hongri
 * @date：8/16/22
 * @description：
 */
class TestMyKotlin {
    fun printFruits() {
        val list: MutableList<String> = ArrayList()
        list.add("Banana")
        list.add("Orange")
        list.add("Grape")
        for (fruit in list) {
            println(fruit)
        }
    }
}