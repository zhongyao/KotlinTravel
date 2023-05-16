package com.hongri.kotlin.range

/**
 * @author：zhongyao
 * @date：2023/5/16
 * @description：
 */
fun main() {
    val score = 990

    if (score in 0..59) {
        System.out.println("不及格")
    } else if (score in 60..100) {
        System.out.println("及格")
    } else if (score !in 0..100) {
        System.out.println("不在正常范围内")
    }

    val week = 5
    var res = when (week) {
        1 -> "一"
        2 -> "二"
        3 -> "三"
        else -> {
            "else"
            println("else")
        }
    }
    println("res:$res")
}