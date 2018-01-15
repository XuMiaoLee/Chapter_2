package com.xyj.chapter_2.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import org.jetbrains.annotations.NotNull
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

class BookManagerService : Service() {

    private val mBookList = CopyOnWriteArrayList<Book>()
//    private val mListenerList = CopyOnWriteArrayList<IOnNewBookArrivedListener>()
    /**
     * 无法完成解注册，因为跨进程传递对象不是同一个，服务端会重新生成
     * 使用RemoteCallbackList来代替CopyOnWriteArrayList
     */
    private val mListenerList = RemoteCallbackList<IOnNewBookArrivedListener>()

    private var mIsServiceDestroyed = AtomicBoolean(false)
    private lateinit var mServiceWorker: Thread

    private val mBinder = object : IBookManager.Stub() {
        override fun registerListener(listener: IOnNewBookArrivedListener?) {
//            if (!mListenerList.contains(listener)) {
//                mListenerList.add(listener)
//            } else {
//                LogUtils.d("already exists!")
//            }
//            LogUtils.d("listener size : ${mListenerList.size}")
            mListenerList.register(listener)
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
//            if (mListenerList.contains(listener)) {
//                mListenerList.remove(listener)
//            } else {
//                LogUtils.d("no found,can not unregister!")
//            }
            mListenerList.unregister(listener)
        }

        override fun getBookList(): MutableList<Book> = mBookList


        override fun addBook(book: Book?) {
            mBookList.add(book)
        }

    }

    private fun onNewBookArrived(@NotNull book: Book) {
        mBookList.add(book)

        val N = mListenerList.beginBroadcast()

        for (i in 0..N) {

        }

        mListenerList.finishBroadcast()
    }

    override fun onBind(intent: Intent): IBinder? = mBinder

    override fun onCreate() {
        super.onCreate()
        mBookList.add(Book(1, "Android"))
        mBookList.add(Book(2, "iOS"))
        mServiceWorker = Thread(ServiceWorker())
        mServiceWorker.start()
    }

    inner class ServiceWorker : Runnable {
        override fun run() {
            while (!mIsServiceDestroyed.get()) {
                Thread.sleep(5000)
                val index = mBookList.size + 1
                onNewBookArrived(Book(index, "new book #$index"))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mIsServiceDestroyed.set(true)

    }

}
