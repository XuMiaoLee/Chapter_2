package com.xyj.chapter_2.aidl

import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import android.os.RemoteCallbackList
import com.xyj.chapter_2.utils.LogUtils
import org.jetbrains.annotations.NotNull
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 客户端调用服务端的方法都运行在Binder线程池当中，如果我们明确该方法是耗时的，那么在客户端调用的时候
 * 我们应该避免这类问题，防止客户端调用方法时线程被挂起，导致的ANR。
 */
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

        /**
         * 2.使用onTransact来加入权限验证
         */
        override fun onTransact(code: Int, data: Parcel?, reply: Parcel?, flags: Int): Boolean {
            if (checkCallingOrSelfPermission("com.xyj.chapter_2.permission.ACCESS_BOOK_SERVICE") != PackageManager.PERMISSION_GRANTED) {
                return false
            }
            var packageName: String? = null
            val packages = packageManager.getPackagesForUid(Binder.getCallingUid())
            packages.forEachIndexed { index, s ->
                if (index == 0) {
                    packageName = s
                }
            }
            if (packageName != "com.xyj") {
                return false
            }
            return super.onTransact(code, data, reply, flags)
        }

        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            mListenerList.register(listener)

            val N = mListenerList.beginBroadcast()
            mListenerList.finishBroadcast()
            LogUtils.d("registerListener, current size:" + N)
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
            val success = mListenerList.unregister(listener)
            if (success) {
                LogUtils.d("success unregisterListener")
            } else {
                LogUtils.d("not found，can not unregister")
            }

            val N = mListenerList.beginBroadcast()
            mListenerList.finishBroadcast()
            LogUtils.d("unregisterListener, current size:" + N)
        }

        override fun getBookList(): MutableList<Book> {
            //演示ANR
//            Thread.sleep(5000)
            //演示异常，回调DeathRecipient
            return mBookList
        }


        override fun addBook(book: Book?) {
            mBookList.add(book)
        }

    }

    private fun onNewBookArrived(@NotNull book: Book) {
        mBookList.add(book)

        val N = mListenerList.beginBroadcast()

        (0 until N)
                .map { mListenerList.getBroadcastItem(it) }
                .forEach { it?.onNewBookArrived(book) }

        mListenerList.finishBroadcast()
    }

    override fun onBind(intent: Intent): IBinder? {
        //1.使用自定义权限来加入权限验证
        if (checkCallingOrSelfPermission("com.xyj.chapter_2.permission.ACCESS_BOOK_SERVICE") != PackageManager.PERMISSION_GRANTED) {
            return null
        }
        return mBinder
    }

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
