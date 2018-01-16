// IOnNewBookArrivedListener.aidl
package com.xyj.chapter_2.aidl;
import com.xyj.chapter_2.aidl.Book;
//监听是否有新书

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book newBook);
}
