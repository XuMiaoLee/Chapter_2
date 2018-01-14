package com.xyj.chapter_2.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.xyj.chapter_2.R
import com.xyj.chapter_2.utils.LogUtils

class BookManagerActivity : AppCompatActivity() {

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val bookManager = IBookManager.Stub.asInterface(service)
            val bookList = bookManager.bookList
            LogUtils.d("query book list, list type: ${bookList.javaClass.canonicalName}")
            LogUtils.d("query book list: $bookList")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_manager)

        val intent = Intent(this, BookManagerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }
}
