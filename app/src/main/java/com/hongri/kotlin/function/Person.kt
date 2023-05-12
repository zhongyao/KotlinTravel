package com.hongri.kotlin.function

/**
 * @author：zhongyao
 * @date：2023/5/12
 * @description：
 */
class Person(var name: String, var age: Int) {
    fun eat() {
        println("$name $age 岁吃柠檬")
    }

    fun work(hour: Int): Int {
        println("work $hour hour, earn $hour * 100元")
        return hour * 100;
    }
}

