package com.hongri.kotlin.util

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.lang.RuntimeException
import java.time.Duration
import java.time.ZoneId

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

/**
 * 获取最大值【通用方法】
 *
 * 1、Java/Kotlin中规定，所有类型的数字都是可比较的，因此必须实现Comparable接口。
 * 2、将max函数修改成接收任意多个实现Comparable接口的参数
 */
fun <T : Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) {
        throw RuntimeException("params can't be empty!!!")
    }
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) {
            maxNum = num
        }
    }
    return maxNum
}

/**
 * 简化Toast的用法
 */
fun String.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

fun Int.showToast(context: Context, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, this, duration).show()
}

/**
 * 简化Snackbar的用法
 */
fun View.showSnackbar(text: String, actionText: String? = null, duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, text, duration)
    if (actionText != null && block != null) {
        snackbar.setAction(actionText) {
            block()
        }
    }
    snackbar.show()
}

fun View.showSnackbar(resId: Int, actionResId: Int? = null, duration: Int = Snackbar.LENGTH_SHORT, block: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, resId, duration)
    if (actionResId != null && block != null) {
        snackbar.setAction(actionResId) {
            block()
        }
    }
    snackbar.show()
}
