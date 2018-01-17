package com.xyj.chapter_2.binderpool

import android.os.IBinder

/*
 * 包名       com.xyj.chapter_2.binderpool
 * 文件名:    BinderPoolImpl
 * 创建者:    xuyj
 * 创建时间:  2018/1/17 on 16:39
 * 描述:     TODO
 */

class BinderPoolImpl : IBinderPool.Stub() {
    override fun queryBinder(binderCode: Int): IBinder? = when (binderCode) {
        BinderPool.BINDER_SECURITY_CENTER -> SecurityCenterImpl()
        BinderPool.BINDER_COMPUTE -> ComputeImpl()
        else -> null
    }
}