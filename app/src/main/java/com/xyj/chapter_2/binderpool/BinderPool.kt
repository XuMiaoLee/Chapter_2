package com.xyj.chapter_2.binderpool

/*
 * 包名       com.xyj.chapter_2.binderpool
 * 文件名:    BinderPool
 * 创建者:    xuyj
 * 创建时间:  2018/1/17 on 16:19
 * 描述:     TODO
 */

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import android.os.RemoteException

import java.util.concurrent.CountDownLatch

class BinderPool private constructor(context: Context) {
    private var mBinderPool: IBinderPool? = null
    private val mContext: Context = context.applicationContext
    private var mConnectBinderPoolCountDownLatch: CountDownLatch? = null

    private val mBinderPoolConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            mBinderPool = IBinderPool.Stub.asInterface(service)
            try {
                mBinderPool!!.asBinder().linkToDeath(mBinderPoolDeathRecipient, 0)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

            mConnectBinderPoolCountDownLatch!!.countDown()
        }

        override fun onServiceDisconnected(name: ComponentName) = Unit
    }

    private val mBinderPoolDeathRecipient = object : IBinder.DeathRecipient {
        override fun binderDied() {
            mBinderPool!!.asBinder().unlinkToDeath(this, 0)
            mBinderPool = null
            //重连
            connectBinderPoolService()
        }
    }

    init {
        connectBinderPoolService()
    }

    @Synchronized private fun connectBinderPoolService() {
        mConnectBinderPoolCountDownLatch = CountDownLatch(1)
        val intent = Intent(mContext, BinderPoolService::class.java)
        mContext.bindService(intent, mBinderPoolConnection, Context.BIND_AUTO_CREATE)
        try {
            mConnectBinderPoolCountDownLatch!!.await()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    fun queryBinder(binderCode: Int): IBinder? {
        var binder: IBinder? = null
        if (binder == null) {
            try {
                binder = mBinderPool!!.queryBinder(binderCode)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }

        }
        return binder
    }

    companion object {
        val BINDER_NONE = -1
        val BINDER_COMPUTE = 0
        val BINDER_SECURITY_CENTER = 1

        @Volatile private var sInstance: BinderPool? = null

        fun getInstance(context: Context): BinderPool? {
            if (sInstance == null) {
                synchronized(Binder::class.java) {
                    if (sInstance == null) {
                        sInstance = BinderPool(context)
                    }
                }
            }
            return sInstance
        }

    }
}
