package com.hongri.kotlin.chapter10

import android.app.IntentService
import android.content.Intent
import android.util.Log
import com.hongri.kotlin.util.getThreadName

/**
 * IntentService
 */
class MyIntentService : IntentService("MyIntentService") {

    /**
     * 运行在单独的线程中，且任务完成后自动停止。
     */
    override fun onHandleIntent(intent: Intent?) {
        Log.d("MyIntentService", getThreadName())
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyIntentService", "onDestroy")
    }

}