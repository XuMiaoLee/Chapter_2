package com.xyj.chapter_2

import android.app.ActivityManager.RunningAppProcessInfo
import android.content.Context.ACTIVITY_SERVICE
import android.app.ActivityManager
import android.content.Context
import java.io.Closeable
import java.io.IOException


/**
 * Created by xuyongjun on 2018/1/10.
 */
object MyUtils {

    /**
     * 获取进程名称
     */
    fun getProcessName(cxt: Context, pid: Int): String? {
        val am = cxt
                .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        return runningApps
                .firstOrNull { it.pid == pid }
                ?.processName
    }

    fun close(closeable: Closeable?) {
        try {
            closeable?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    fun executeInThread(runnable: Runnable) {
        Thread(runnable).start()
    }

}