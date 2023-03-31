package com.hongri.kotlin.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author：zhongyao
 * @date：2023/3/31
 * @description：StateFlow
 */
class Test {
    private val _state = MutableStateFlow<String>("unknown")
    val state: StateFlow<String> get() = _state

    fun getApi(scope: CoroutineScope) {
        scope.launch {
            val res = getApi()
            _state.value = res
        }
    }

    private suspend fun getApi() = withContext(Dispatchers.IO) {
        delay(2000)
        "hello StateFlow"
    }
}