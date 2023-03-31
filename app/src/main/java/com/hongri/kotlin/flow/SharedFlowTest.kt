package com.hongri.kotlin.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * @author：zhongyao
 * @date：2023/3/31
 * @description：SharedFlow
 */
class SharedFlowTest {
    private val _state = MutableSharedFlow<Int>(replay = 3, extraBufferCapacity = 2)
    val state: SharedFlow<Int> get() = _state;

    fun getApi(scope: CoroutineScope) {
        scope.launch {
            for (i in 1..20) {
                delay(200)
                _state.emit(i)
                println("send data: $i")
            }
        }
    }
}