package com.hongri.kotlin.string

/**
 * @author：zhongyao
 * @date：2023/5/16
 * @description：
 */

fun main() {
    val isLogin = false
    println("server response result:${if (isLogin) "登录成功了" else "登录失败了"}")
}