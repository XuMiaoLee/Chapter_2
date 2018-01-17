package com.xyj.chapter_2.binderpool

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.R

class BinderPoolActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder_pool)

        Thread{
            val compute = BinderPool.getInstance(this)?.queryBinder(BinderPool.BINDER_COMPUTE) as ICompute
        }.start()
    }
}
