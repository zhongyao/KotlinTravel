package com.hongri.kotlin.channel

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * @author：zhongyao
 * @date：2023/3/27
 * @description：Channel通道--用于协程间通信
 * @reference：https://www.cnblogs.com/joy99/p/15805928.html
 */
object TestChannel {

    /**
     * Channel 简单使用
     */
    suspend fun test1() = coroutineScope {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                channel.send(x * x)
            }
        }

        repeat(5) {
            println(channel.receive())
        }

        println("Done!!")
    }

    /**
     * Channel 迭代
     */
    suspend fun testChannelIterator() = coroutineScope {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                channel.send(x * x)
            }

            /***
             * 结束发送:
             * 对于一个 Channel，如果我们调用了它的 close，它会立即停止接受新元素，
             * 也就是说这时候它的 isClosedForSend 会立即返回 true，而由于 Channel 缓冲区的存在，
             * 这时候可能还有一些元素没有被处理完，所以要等所有的元素都被读取之后 isClosedForReceive 才会返回 true。
             */
            channel.close()
        }

//        val iterator = channel.iterator()
//        while (iterator.hasNext()) {
//            val next = iterator.next()
//            println(next)
//        }

        //or
        for (y in channel) {
            println(y)
        }

        //【如果没有调用上面的 channel.close()】最后一行 Done! 没有打印出来，并且程序没有结束。其实是一直在等待读取 Channel 中的数据，只要有数据到了，就会被读取到。
        println("Channel Iterator Done!!")
    }
}