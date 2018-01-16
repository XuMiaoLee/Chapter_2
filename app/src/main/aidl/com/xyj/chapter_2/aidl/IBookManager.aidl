// IBookManager.aidl
package com.xyj.chapter_2.aidl;

import com.xyj.chapter_2.aidl.Book;
import com.xyj.chapter_2.aidl.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);
}
