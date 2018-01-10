package com.xyj.chapter_2

import android.app.Application
import android.os.Process
import android.util.Log
import com.xyj.chapter_2.utils.LogUtils


/**
 * Created by xuyongjun on 2018/1/10.
 */
class App : Application() {

    companion object {
        val TAG: String = App::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()

        //多进程下Application会调用多次
        val processName = MyUtils.getProcessName(applicationContext,
                Process.myPid())
        LogUtils.d("application start, process name:" + processName)
    }
}