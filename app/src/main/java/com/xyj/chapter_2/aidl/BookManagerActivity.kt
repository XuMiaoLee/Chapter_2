package com.xyj.chapter_2.aidl

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.R
import com.xyj.chapter_2.utils.LogUtils

class BookManagerActivity : AppCompatActivity() {

    private lateinit var mRemoteBookManager: IBookManager

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mRemoteBookManager = IBookManager.Stub.asInterface(service)
            //查询
            var bookList = mRemoteBookManager.bookList
            LogUtils.d("query book list, list type: ${bookList.javaClass.canonicalName}")
            LogUtils.d("query book list: $bookList")
            //添加后查询
            mRemoteBookManager.addBook(Book(3, "Java"))
            bookList = mRemoteBookManager.bookList
            LogUtils.d("query book list, list type: ${bookList.javaClass.canonicalName}")
            LogUtils.d("query book list: $bookList")
            //注册监听
            mRemoteBookManager.registerListener(mOnNewBookArrivedListener)
        }

        override fun onServiceDisconnected(name: ComponentName?) = Unit

    }

    private val mOnNewBookArrivedListener = object : IOnNewBookArrivedListener.Stub() {
        override fun onNewBookArrived(newBook: Book?) = LogUtils.d("received new book $newBook")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_manager)

        val intent = Intent(this, BookManagerService::class.java)
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive) {
            mRemoteBookManager.unregisterListener(mOnNewBookArrivedListener)
        }
        unbindService(mConnection)
        super.onDestroy()
    }
}
