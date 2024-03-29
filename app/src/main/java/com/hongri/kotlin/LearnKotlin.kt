package com.hongri.kotlin

import com.hongri.kotlin.channel.TestChannel
import com.hongri.kotlin.chapter2.CellPhone
import com.hongri.kotlin.chapter2.CellPhone2
import com.hongri.kotlin.chapter2.Singleton
import com.hongri.kotlin.chapter2.Student
import com.hongri.kotlin.chapter5.lettersCount
import com.hongri.kotlin.coroutine.TestCoroutine
import com.hongri.kotlin.flow.TestFlow
import kotlinx.coroutines.CoroutineScope
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
    //扩展函数调用
    val count = "abc123xyz@@".lettersCount()
    println("count:$count")

    runBlocking {
//        testChannel(this)
//        testFlow(this)
        testCoroutine(this)
    }

}

/**
 * 协程Coroutine调用
 */
suspend fun testCoroutine(coroutineScope: CoroutineScope) {
//    TestCoroutine.coroutineScope(coroutineScope)
//    TestCoroutine.coroutineCancel(coroutineScope)
//    TestCoroutine.async(coroutineScope)
//    TestCoroutine.timeout(coroutineScope)
//    TestCoroutine.asyncMulti(coroutineScope)
    TestCoroutine.coroutineDataSync(coroutineScope)
}

/**
 * 通道Channel调用
 */
suspend fun testChannel(coroutineScope: CoroutineScope) {
//        TestChannel.test1()
//        TestChannel.testChannelIterator()
    TestChannel.channelInteract(coroutineScope)
}

/**
 * 数据流Flow调用
 */
suspend fun testFlow(scope: CoroutineScope) {
//    TestFlow.createFlow().collect {
//        println(it)
//    }
//
//    //超时取消
//    withTimeoutOrNull(250) {
//        TestFlow.flowCancel().collect {
//            println(it)
//        }
//    }

//        val sum = TestFlow.reduce()
//        println(sum)
//        val sumFold = TestFlow.fold()
//        println(sumFold)

//        TestFlow.launchIn(this)

//        TestFlow.flowContinuous()
//
//        TestFlow.flowOnStart()

//        TestFlow.flowOnCompletion()

//        TestFlow.buffer()

//    TestFlow.conflate()

//    TestFlow.catch()

//    TestFlow.retry()

//    TestFlow.flowOn()

//    TestFlow.map()

//    TestFlow.transform()
//    TestFlow.filter()
//    TestFlow.zip()
//    TestFlow.combine()
//    TestFlow.flattenContact()
//    TestFlow.flattenMerge()
//    TestFlow.flatMapMerge()
//    TestFlow.flatMapLatest()
//    TestFlow.testStateFlow(scope)
    TestFlow.testSharedFlow(scope)

}

fun largerNum(num1: Int, num2: Int): Int {
    return max(num1, num2);
}