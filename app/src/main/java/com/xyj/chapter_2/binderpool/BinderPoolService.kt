package com.xyj.chapter_2.binderpool

import android.app.Service
import android.content.Intent
import android.os.IBinder

class BinderPoolService : Service() {

    private val mBinderPool: IBinderPool = BinderPoolImpl()

    override fun onBind(intent: Intent): IBinder? = mBinderPool.asBinder()
}
