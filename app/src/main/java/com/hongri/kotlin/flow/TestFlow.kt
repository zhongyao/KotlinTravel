package com.hongri.kotlin.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce

/**
 * @author：zhongyao
 * @date：2023/3/27
 * @description：数据流Flow -- 是冷流（惰性的）-- 即在调用末端流操作符(collect 是其中之一)之前， flow{ ... } 中的代码不会执行。
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

}