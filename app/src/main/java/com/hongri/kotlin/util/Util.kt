package com.hongri.kotlin.util

import android.util.Log

/**
 * @author：hongri
 * @date：8/7/22
 * @description：单例类
 */
object Util {
    private const val TAG = "Util"
    fun doAction() {
        println("do action")
    }

    /**
     * JvmOverloads作用
     */
    @JvmOverloads
    fun f(a: String, b: Int, c: String = "abc") {
        Log.d(TAG, "a:$a b:$b c:$c");
    }

    /**
     * Kotlin中的MutableList与List有什么区别
     */
    fun fList() {
        val mutableList = mutableListOf<Int>()
        //参数element
        mutableList.add(1)
        mutableList.add(2)
        mutableList.add(3)
        //参数element
        mutableList.remove(2)
        //参数index
        mutableList.removeAt(0)
        //参数index
        val element = mutableList[0]
        //参数index element
        mutableList[0] = 100;


        val list = listOf<Int>(10, 30)

        Log.d(TAG, "mutableList:$mutableList list:$list")

    }
}