package com.hongri.kotlin.chapter10.covariation

/**
 * @author：hongri
 * @date：8/27/22
 * @description：泛型协变
 */

//这里SimpleData类已经进行了协变声明，那么SimpleData<Student> 自然就是SimpleData<Person>的子类了
class SimpleData<out T>(private val data: T?) {
    fun get(): T? {
        return data
    }
}

/**
 * kotlin构造方法的参数带有val var 跟上面都没有带的区别
 * 1、带var：相当于在该类中声明了一个private的成员变量
 * 2、带val：相当于添加了final的变量
 * 3、不带：只是传递值，不可在该类中使用
 */
open class Person(val name: String, val age: Int)
class Student(name: String, age: Int): Person(name, age)
class Teacher(name: String, age: Int): Person(name, age)