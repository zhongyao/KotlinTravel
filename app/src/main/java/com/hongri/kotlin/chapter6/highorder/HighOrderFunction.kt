package com.hongri.kotlin.chapter6.highorder

/**
 * @author：hongri
 * @date：8/12/22
 * @description：
 */

/**
 * 高阶函数：
 * 如果一个函数接收另一个函数作为参数，或者返回值的类型是另一个函数，那么该函数就称为高阶函数。
 */
fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int): Int {
    val result = operation(num1, num2)
    return result
}


fun plus(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun minus(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun main() {
    val num1 = 100
    val num2 = 20
    //::plus 表示函数引用方式的写法
    val result1 = num1AndNum2(num1, num2, ::plus)
    val result2 = num1AndNum2(num1, num2, ::minus)
    println("result1 is $result1")
    println("result2 is $result2")
}

/**
 * Lambda表达式是最常用也是最普遍的高阶函数调用方式
 */
fun main1() {
    val num1 = 100
    val num2 = 20
    val result1 = num1AndNum2(num1, num2) {n1, n2 -> n1 + n2}
    val result2 = num1AndNum2(num1, num2) {n1, n2 -> n1 - n2}
    println("result1 is $result1")
    println("result2 is $result2")
}

