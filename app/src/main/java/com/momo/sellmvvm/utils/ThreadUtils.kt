package com.momo.sellmvvm.utils

import android.os.Handler
import android.os.HandlerThread

object ThreadUtils {

    val mainHandler : Handler = Handler()
    val taskHandler : Handler

    init {
        val taskThread = HandlerThread("taskThread")
        taskThread.start()
        taskHandler = Handler(taskThread.looper)
    }

}