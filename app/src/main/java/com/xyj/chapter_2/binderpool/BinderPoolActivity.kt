package com.xyj.chapter_2.binderpool

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.R
import com.xyj.chapter_2.utils.LogUtils

class BinderPoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder_pool)
        //这边要开线程处理，因为BinderPool中用了CountDownLatch，可能会阻塞UI线程
        Thread {
            val binderPool = BinderPool.getInstance(this)
            val computeBinder = binderPool?.queryBinder(BinderPool.BINDER_COMPUTE)
            val compute = ICompute.Stub.asInterface(computeBinder)
            val result = compute.add(1, 2)
            LogUtils.d("1 + 2 = $result")

            val securityBinder = binderPool?.queryBinder(BinderPool.BINDER_SECURITY_CENTER)
            val security = ISecurityCenter.Stub.asInterface(securityBinder)
            val encrypt = security.encrypt("android")
            LogUtils.d("encrypt=$encrypt")
            val decrypt = security.decrypt(encrypt)
            LogUtils.d("decrypt=$decrypt")
        }.start()
    }
}
