package com.hongri.kotlin.chapter2

open class Person(val name: String, val age: Int) {
    /**
     * var与val区别：
     * val相当于常量
     * var相当于变量
     */
//    var name = ""
//    var age = 0

    fun eat() {
        println("$name is eating. He is $age years old.")
    }
}