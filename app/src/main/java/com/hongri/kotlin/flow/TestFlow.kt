package com.hongri.kotlin.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.reduce
import okhttp3.Dispatcher
import kotlin.system.measureTimeMillis

/**
 * @author：zhongyao
 * @date：2023/3/27
 * @description：数据流Flow -- 是【冷流】（惰性的）-- 即在调用末端流操作符(collect 是其中之一)之前， flow{ ... } 中的代码不会执行。
 * @reference：https://www.cnblogs.com/joy99/p/15805955.html
 */
object TestFlow {

    /**
     * 使用emit()方法来发射数据
     * 使用collect()方法来收集结果
     */
    fun createFlow(): Flow<Int> = flow {
        delay(1000)
        emit(1)
        delay(1000)
        emit(2)
        delay(1000)
        emit(3)
    }


    /**
     * 使用flowOf 或 asFlow 可以将各种集合与序列转换成流，不需要调用emit()发射数据
     */
    fun createFlow2(): Flow<Int> = flowOf(1, 2, 3).onEach {
        delay(1000)
    }

    fun createFlow3(): Flow<Int> = listOf(1, 2, 3).asFlow().onEach {
        delay(1000)
    }

    /**
     * Flow的取消
     */
    fun flowCancel(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            println("Emitting $i")
            emit(i)
        }
    }

    /**
     *  reduce 函数，能够对集合进行计算操作
     */
    suspend fun reduce(): Int = (1..5).asFlow().reduce { a, b ->
        a + b
    }

    /**
     * fold需要设置一个初始值
     */
    suspend fun fold(): Int = (1..5).asFlow().fold(100) { a, b ->
        a + b
    }

    /**
     * launchIn 用来在指定的 CoroutineScope 内启动 flow， 需要传入一个参数： CoroutineScope
     */
    fun launchIn(coroutineScope: CoroutineScope) {
        (1..5)
            .asFlow()
            .onEach { delay(100) }
            .flowOn(Dispatchers.IO)
            .onEach { println(it) }
            .launchIn(coroutineScope)

        flowOf("one", "two", "three", "four", "five")
            .onEach { delay(200) }
            .flowOn(Dispatchers.IO)
            .onEach { println(it) }
            .launchIn(coroutineScope)
    }

    /**
     * 与 Sequence 类似，Flow 的每次单独收集都是按顺序执行的，除非进行特殊操作的操作符使用多个流。
     * 默认情况下不启动新协程。 从上游到下游每个过渡操作符都会处理每个发射出的值然后再交给末端操作符。
     */
    suspend fun flowContinuous() {
        (1..5)
            .asFlow()
            .filter {
                println("Filter $it")
                it % 2 == 0
            }
            .map {
                println("Map $it")
                "string $it"
            }
            .collect {
                println("Collect $it")
            }
    }

    /**
     * Flow 启动开始执行时的start回调
     */
    suspend fun flowOnStart() {
        (1..5)
            .asFlow()
            .onEach {
                delay(100)
            }
            .onStart {
                println("onStart -- 在耗时操作时可在此处做loading")
            }
            .collect {
                println(it)
            }
    }

    suspend fun flowOnCompletion() {
        flow {
            for (i in 1..5) {
                delay(100)
                emit(i)
            }
        }.onCompletion {
            println("onCompletion")
        }.collect { println(it) }
    }

    /**
     * buffer 对应 Rxjava 的 BUFFER 策略。 buffer 操作指的是设置缓冲区。当然缓冲区有大小，如果溢出了会有不同的处理策略。
     *
     * 【SUSPEND】设置缓冲区，如果溢出了，则将当前协程挂起，直到有消费了缓冲区中的数据。
     * 【DROP_OLDEST】设置缓冲区，如果溢出了，丢弃最新的数据。
     * 【DROP_LATEST】设置缓冲区，如果溢出了，丢弃最老的数据。
     * 缓冲区的大小可以设置为 0，也就是不需要缓冲区。
     */
    suspend fun buffer() {
        val costTime = measureTimeMillis {
            (1..5).asFlow().onEach {
                delay(100)
                println("produce data: $it")
            }.buffer(2, BufferOverflow.SUSPEND)
                .collect {
                    delay(700)
                    println("collect: $it")
                }
        }
        println("costTime: $costTime")
    }

    /**
     * conflate 操作符可以用于跳过中间值：
     * 此操作符是不设缓冲区，也就是缓冲区大小为 0，丢弃旧数据，也就是采取 DROP_OLDEST 策略，
     * 其实等价于 buffer(0, BufferOverflow.DROP_OLDEST) 。
     */
    suspend fun conflate() {
        val costTime = measureTimeMillis {
            (1..5).asFlow().onEach {
                delay(100)
                println("produce data: $it")
            }.conflate()
                .collect {
                    delay(700)
                    println("collection:$it")
                }
        }
        println("conflate -- costTime: $costTime")
    }

}