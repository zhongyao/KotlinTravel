package com.hongri.kotlin.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

/**
 * @author：zhongyao
 * @date：2023/4/10
 * @description：
 * @reference：
 * https://www.cnblogs.com/joy99/p/15805916.html
 * https://bbs.huaweicloud.com/blogs/363080
 *
 * 协程设计的初衷是为了解决并发问题，可以更方便的处理多线程协作的任务
 * 协程就是一个封装好的线程框架 + 状态机
 */
object TestCoroutine {


    /**
     * 创建协程：通过 CoroutineContext 创建一个 CoroutineScope 对象
     */
    suspend fun coroutineScope(coroutineScope: CoroutineScope) {
        coroutineScope.launch(Dispatchers.Main) {//在主线程中启动一个协程
            val result = withContext(Dispatchers.Default) {//切换到子线程执行
                //doSth
            }
//            handleResult(result) //切换到主线程
        }
    }

    /**
     * 协程取消：
     * 为了能够停止协程工作，需要定期检测协程是否处于active状态
     */
    suspend fun coroutineCancel(coroutineScope: CoroutineScope) {
        val startTime = System.currentTimeMillis()
        val job = coroutineScope.launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
                //ensureActive() 在协程不在 active 状态时会立即抛出异常,不再进行后续操作
                ensureActive()
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: hello ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)
        println("main: ready to cancel!")
        job.cancelAndJoin() // 取消一个作业并且等待它结束
        println("main: Now cancel.")
    }

    /**
     * 需要返回值：
     * 可使用async函数创建
     */
    suspend fun async(coroutineScope: CoroutineScope) {
        val asyncDeferred = coroutineScope.async {
            for (i in 1..5) {
                println("i == $i")
            }
        }

        val result = asyncDeferred.await()
        println("result:$result")
    }

    /**
     * 协程的超时
     */
    suspend fun timeout(coroutineScope: CoroutineScope) {
        withTimeout(300) {
            println("start")
            delay(100)
            println("progress 1")
            delay(100)
            println("progress 2")
            delay(100)
            println("progress 3")
            delay(100)
            println("progress 4")
            delay(100)
            println("progress 5")
            println("end")
        }
    }

    /**
     * 使用async并发
     */
    suspend fun asyncMulti(coroutineScope: CoroutineScope) {
        val time = measureTimeMillis {
            val a = coroutineScope.async(Dispatchers.Default) {
                println("a")
                delay(2000)
                1
            }

            val b = coroutineScope.async(Dispatchers.Default) {
                println("b")
                delay(3000)
                2
            }

            println("${a.await()} + ${b.await()}")
            println("end")
        }
        println("time: $time")
    }

    /**
     * 协程数据同步问题解决方案：
     * 1、AtomicInteger
     * 2、synchronized
     * 3、ReentrantLock
     * 4、Mutex
     * 5、限制线程（仅一个）
     */
    private var count = AtomicInteger()
    suspend fun coroutineDataSync(coroutineScope: CoroutineScope) {
        repeat(100) {
            coroutineScope.launch(Dispatchers.Default) {
                repeat(1000) {
                    count.incrementAndGet()
                }
            }
        }

        coroutineScope.launch(Dispatchers.Default) {
            delay(300)
            printWithThreadInfo("end count: $count")
        }
    }
}

private fun printWithThreadInfo(s: String) {
    println("thread id -- ${Thread.currentThread().id}  thread name: ${Thread.currentThread().name} s: $s")
}