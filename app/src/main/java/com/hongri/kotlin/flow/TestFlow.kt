package com.hongri.kotlin.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flattenMerge
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
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.lang.Exception
import java.util.concurrent.Executors
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

    /**
     * catch 操作符捕获上游异常
     */
    suspend fun catch() {
        (1..5).asFlow().onEach {
            if (it == 4) {
                throw Exception("test exception")
            }
            delay(100)
            println("produce data: $it")
            //catch 操作捕获到异常之后，不会再影响下游：
        }.catch { ex ->
            println("catch exception: ${ex.message}")
        }.onCompletion {
            //onCompletion 即使遇到异常也会执行
            println("onCompletion")
        }.collect {
            println("collect: $it")
        }
    }

    /**
     * retry、retryWhen 操作符重试
     */
    suspend fun retry() {
        (1..5).asFlow().onEach {
            if (it == 4) {
                throw Exception("test exception")
            }
            delay(100)
            println("produce data: $it")
        }.retry(2) {
            it.message == "test exception"
        }.catch { ex ->
            println("catch exception: ${ex.message}")
        }.collect {
            println("collect: $it")
        }
    }


    /**
     * Flows 通过 flowOn 方法来切换线程，多次调用，都会影响到它上游的代码:
     *
     * 发射数据是在 Dispatchers.IO 线程执行的，
     * map 操作时在我们自定义的线程池中进行的，
     * collect 操作在 Dispatchers.Main 线程进行。
     */
    private val mDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
    suspend fun flowOn() {
        (1..5).asFlow().onEach {
            printWithThreadInfo("produce data: $it")
        }.flowOn(Dispatchers.IO).map {
            printWithThreadInfo("$it to String")
            "String: $it"
        }.flowOn(mDispatcher).onCompletion {
            mDispatcher.close()
        }.collect {
            printWithThreadInfo("collect: $it")
        }
    }

    private fun printWithThreadInfo(s: String) {
        println("thread id -- ${Thread.currentThread().id}  thread name: ${Thread.currentThread().name} s: $s")
    }

    /**
     * map 操作符用于 Flow 表示将流中的每个元素进行转换后再发射出来。
     */
    suspend fun map() {
        (1..5).asFlow().map {
            "string $it"
        }.collect {
            println(it)
        }
    }

    /**
     * 在使用 transform 操作符时，可以任意多次调用 emit ，
     * 这是 transform 跟 map 最大的区别:
     */
    suspend fun transform() {
        (1..5).asFlow().transform {
            emit(it * 2)
            delay(100)
            emit(it * 33)
        }.collect {
            println(it)
        }
    }

    /**
     * 按条件过滤
     */
    suspend fun filter() {
        (1..5).asFlow().filter {
            it % 2 == 0
        }.collect {
            println(it)
        }
    }

    /**
     * take 操作符只取前几个 emit 发射的值
     */
    suspend fun take() {
        (1..5).asFlow().take(2).collect {
            println(it)
        }
    }

    /**
     * zip 是可以将2个 flow 进行合并的操作符
     */
    suspend fun zip() {
        val flowA = (1..5).asFlow()
        val flowB = flowOf("one", "two", "three", "four", "five").onEach { delay(200) }
        flowA.zip(flowB) { a, b ->
            "$a + $b"
        }.collect {
            println(it)
        }
    }

    /**
     * combine:
     * 使用 combine 合并时，每次从 flowA 发出新的 item ，会将其与 flowB 的最新的 item 合并。
     */
    suspend fun combine() {
        val flowA = (1..5).asFlow().onEach { delay(1000) }
        val flowB =
            flowOf("one", "two", "three", "four", "five", "six", "seven").onEach { delay(1000) }
        flowA.combine(flowB) { a, b ->
            "$a + $b"
        }.collect {
            println(it)
        }
    }

    /**
     * flattenConcat:
     * 将给定流按顺序展平为单个流，而不交错嵌套流。
     */
    @OptIn(FlowPreview::class)
    suspend fun flattenContact() {
        val flowA = (1..5).asFlow()
        val flowB = flowOf("one", "two", "three", "four", "five").onEach { delay(1000) }
        flowOf(flowA, flowB)
            .flattenConcat()
            .collect {
                println(it)
            }
    }

    @OptIn(FlowPreview::class)
    suspend fun flattenMerge() {
        val flowA = (1..5).asFlow().onEach { delay(200) }
        val flowB = flowOf("one", "two", "three", "four", "five").onEach {
            delay(500)
        }
        flowOf(flowA, flowB).flattenMerge().collect {
            println(it)
        }
    }

    /**
     * flatMapMerge:
     * 由map、flattenMerge 操作符实现
     */
    suspend fun flatMapMerge() {
        (1..5).asFlow().flatMapMerge {
            flow {
                emit(it)
                delay(1000)
                emit("string + $it")
            }
        }.collect {
            println(it)
        }
    }

    /**
     * flatMapMerge 和 flatMapConcat 都是将一个 flow 转换成另一个流。
     * 区别在于：
     * flatMapMerge 不会等待内部的 flow 完成 ，
     * 而调用 flatMapConcat 后，collect 函数在收集新值之前会等待 flatMapConcat 内部的 flow 完成。
     */
    suspend fun flatMapContact() {
        (1..5).asFlow().flatMapConcat {
            flow {
                emit(it)
                delay(1000)
                emit("string + $it")
            }
        }.collect {
            println(it)
        }
    }

    /**
     * flatMapLatest:
     * 当发射了新值之后，上个 flow 就会被取消。
     */
    suspend fun flatMapLatest() {
        (1..5).asFlow().onEach {
            delay(1000)
        }.flatMapLatest {
            flow {
                println("begin flatMapLatest $it")
                delay(2000)
                emit("string + $it")
                println("end flatMapLatest $it")
            }
        }.collect {
            println(it)
        }
    }

    /**
     * StateFlow【后续需深入研究与 LiveData的差异】:
     * StateFlow 是一个状态容器式可观察数据流，可以向其收集器发出当前状态更新和新状态更新。还可通过其 value 属性读取当前状态值。
     * 如需更新状态并将其发送到数据流，请为 MutableStateFlow 类的 value 属性分配一个新值。
     *
     * 在 Android 中，StateFlow 非常适合需要让可变状态保持可观察的类。
     */
    suspend fun testStateFlow(scope: CoroutineScope) {
        val test = Test()
        test.getApi(scope)

        scope.launch(Dispatchers.IO) {
            test.state.collect{
                printWithThreadInfo(it)
            }
        }

        scope.launch(Dispatchers.IO) {
            test.state.collect{
                printWithThreadInfo(it)
            }
        }

    }

    /**
     * SharedFlow：
     * 如果只是需要管理一系列状态更新(即事件流)，而非管理当前状态.则可以使用 SharedFlow 共享流。
     * 如果对发出的一连串值感兴趣，则这API十分方便。相比 LiveData 的版本控制，SharedFlow 则更灵活、更强大。
     * SharedFlow 可以配置对历史发射的数据进行订阅，适合用来处理对于事件的监听。
     */
    suspend fun testSharedFlow(scope: CoroutineScope) {
        val test = SharedFlowTest()
        test.getApi(scope)
        val job = scope.launch(Dispatchers.IO) {
            delay(3000)
            test.state.collect{
                println("---collect1: $it")
            }
        }
        delay(5000)
        // 取消任务， 避免泄漏
        job.cancel()
    }

}