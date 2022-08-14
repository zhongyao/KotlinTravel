package com.hongri.kotlin.chapter8.genericity

/**
 * @author：hongri
 * @date：8/14/22
 * @description：泛型方法
 */
class MineClass {
    fun <T> method(param: T): T {
        return param
    }
}