package com.hongri.kotlin.chapter8.by

/**
 * @author：hongri
 * @date：8/16/22
 * @description：
 * koltin中委托使用的关键字时by，我们只需要在接口声明的后面使用by关键字，再接上受委托的辅助对象
 * 就可以免去之前所写的一大推模板式的代码了
 */
class MySet<T>(private val helperSet: HashSet<T>) : Set<T> by helperSet {

    fun helloWorld() = println("Hello World")
    override fun isEmpty() = false

}