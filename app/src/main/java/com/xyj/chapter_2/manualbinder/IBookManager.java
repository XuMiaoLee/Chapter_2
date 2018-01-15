package com.xyj.chapter_2.manualbinder;

import android.os.IInterface;

import com.xyj.chapter_2.aidl.Book;

/**
 * Created by xuyongjun on 2018/1/11.
 */

public interface IBookManager extends IInterface
{

    static final String DESCRIPTOR = "com.xyj.chapter_2.manualbinder.IBookManager";

    static final int TRANSACTION_getBookList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    static final int TRANSACTION_addBook     = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);

    public java.util.List<Book> getBookList() throws android.os.RemoteException;

    public void addBook(Book book) throws android.os.RemoteException;

}
