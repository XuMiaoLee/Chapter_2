package com.xyj.chapter_2.binderpool

/*
 * 包名       com.xyj.chapter_2.binderpool
 * 文件名:    ComputeImpl
 * 创建者:    xuyj
 * 创建时间:  2018/1/17 on 15:26
 * 描述:     TODO
 */

import android.os.RemoteException

class ComputeImpl : ICompute.Stub() {

    @Throws(RemoteException::class)
    override fun add(a: Int, b: Int): Int = a + b
}
