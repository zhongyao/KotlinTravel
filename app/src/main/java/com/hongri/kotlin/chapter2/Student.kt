package com.hongri.kotlin.chapter2

/**
 * Kotlin构造函数分两种：主构造函数和此构造函数
 */
class Student(val sno: String, val grade: Int, name: String, age: Int) : Person(name, age) , Study{

    /**
     * 主构造函数没有函数体，主构造函数的逻辑可以写在init结构体中。
     */
    init {
        println("sno is $sno")
        println("grade is $grade")
    }

    /**
     * 次构造函数通过constructor来定义
     * Kotlin规定，当一个类中既有主构造函数又有次构造函数时，所有的次构造函数都必须调用主构造函数(包含间接调用)
     */
    constructor(name: String, age: Int) : this("", 0, name, age) {

    }

    constructor() : this("", 0) {

    }

    override fun readBooks() {
        println("$name is readBooks")
    }

    override fun doHomework() {
        println("$name is doHomework")
    }

}