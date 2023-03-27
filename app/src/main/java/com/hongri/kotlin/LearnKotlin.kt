package com.hongri.kotlin

import com.hongri.kotlin.chapter2.CellPhone
import com.hongri.kotlin.chapter2.CellPhone2
import com.hongri.kotlin.chapter2.Singleton
import com.hongri.kotlin.chapter2.Student
import com.hongri.kotlin.flow.TestFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
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
    val cellPhone2 = CellPhone2("HuaWei")
    //copy函数
    val cellPhone3 = cellPhone2.copy(price = 2000.0)
    //解析式声明
    val (brand, price) = CellPhone("Huawei", 8000.0)

    println(cellPhone1)
    println(cellPhone2)
    println(cellPhone3)
    println("brand:$brand price:$price")

    //单例调用
    Singleton.singletonTest()

    runBlocking {
        //通道Channel调用
//        TestChannel.test1()
//        TestChannel.testChannelIterator()

        //数据流Flow调用
        TestFlow.createFlow().collect {
            println(it)
        }

        //超时取消
        withTimeoutOrNull(250) {
            TestFlow.flowCancel().collect {
                println(it)
            }
        }

        val sum = TestFlow.reduce()
        println(sum)
        val sumFold = TestFlow.fold()
        println(sumFold)

    }

}

fun largerNum(num1: Int, num2: Int): Int {
    return max(num1, num2);
}