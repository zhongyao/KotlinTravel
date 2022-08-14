package com.hongri.kotlin.chapter8.genericity

/**
 * @author：hongri
 * @date：8/14/22
 * @description：泛型类---
 */
class MyClass<T> {
    fun method(param: T): T {
        return param
    }

    /**
     * Kotlin运行我们对泛型的类型进行限制，如下这种写法表示只能将method方法的泛型指定成数字类型
     * 比如Int、Float、Double等
     */
    fun <T : Number> method(param: T): T {
        return param
    }
}