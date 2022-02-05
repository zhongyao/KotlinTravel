package com.hongri.kotlin

import com.hongri.kotlin.chapter2.CellPhone
import com.hongri.kotlin.chapter2.Person
import com.hongri.kotlin.chapter2.Singleton
import com.hongri.kotlin.chapter2.Student
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

//    val p = Person()
//    p.name = "Jack"
//    p.age = 18
//    p.eat()

    val student = Student("234", 6, "Jack", 23)
    student.eat()
    student.readBooks()
    student.doHomework()

    val cellPhone1 = CellPhone("HuaWei", 3590.0)
    val cellPhone2 = CellPhone("HuaWei", 3590.0)
    println(cellPhone1)
    println(cellPhone2)
    println("cellPhone1 equals cellPhone2 " + (cellPhone1 == cellPhone2))

    //单例调用
    Singleton.singletonTest()
}

fun largerNum(num1: Int, num2: Int): Int {
    return max(num1, num2);
}