package com.hongri.kotlin

import kotlin.math.max

/**
 * Create by hongri on 2021/3/27
 * Description:
 */
fun main() {
    val a = 10;
    val b = 20;
    val c = largerNum(10, 20)
    println("c=$c")
}

fun largerNum(num1: Int, num2: Int): Int {
    return max(num1, num2);
}