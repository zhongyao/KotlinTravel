package com.hongri.kotlin.util

/**
 * @author：hongri
 * @date：8/7/22
 * @description：顶层方法--Kotlin编译器会将所有的顶层方法全部编译成静态方法
 */

fun doSomething() {
    println("do something!!")
}

/**
 * 获取线程名字
 */
fun getThreadName(): String {
    return "Thread id is ${Thread.currentThread().name}"
}