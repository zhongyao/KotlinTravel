package com.hongri.kotlin.chapter2

/**
 * 在Kotlin中创建一个单例类只需要将class关键字改成object关键字即可
 */
object Singleton {

    fun singletonTest() {
        println("singletonTest is called.")
    }
}