package com.xyj.chapter_2.aidl

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.concurrent.CopyOnWriteArrayList

class BookManagerService : Service() {

    private val mBookList = CopyOnWriteArrayList<Book>()

    private val mBinder = object : IBookManager.Stub() {
        override fun getBookList(): MutableList<Book> = mBookList


        override fun addBook(book: Book?) {
            mBookList.add(book)
        }

    }

    override fun onBind(intent: Intent): IBinder? {
        mBookList.add(Book(1, "Android"))
        mBookList.add(Book(2, "iOS"))
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()

    }
}
