// IBookManager.aidl
package com.xyj.chapter_2.aidl;

import com.xyj.chapter_2.aidl.Book;

interface IBookManager {

    List<Book> getBookList();

    void addBook(in Book book);
}
