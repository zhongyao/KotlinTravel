package com.hongri.kotlin.chapter10.genericity

import android.content.Context
import android.content.Intent

/**
 * @author：hongri
 * @date：8/27/22
 * @description：泛型实化
 */
//启动Activity的一种新写法
inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}