package com.xyj.chapter_2.provider

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.R
import com.xyj.chapter_2.utils.LogUtils

class ProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

//        val uri = Uri.parse("content://com.xyj.chapter_2.book.provider")
//        contentResolver.query(uri, null, null, null, null)
//        contentResolver.query(uri, null, null, null, null)
//        contentResolver.query(uri, null, null, null, null)

        val bookUri = Uri.parse("content://com.xyj.chapter_2.book.provider/${DbOpenHelper.BOOK_TABLE_NAME}")
        //插入
        val values = ContentValues()
        values.put("name", "Java")
        values.put("_id", 4)
        contentResolver.insert(bookUri, values)
        //查询
        val cursor = contentResolver.query(bookUri, null, null, null, null)
        while (cursor.moveToNext()) {
            val bookName = cursor.getString(cursor.getColumnIndex("name"))
            val bookId = cursor.getString(cursor.getColumnIndex("_id"))
            LogUtils.d("book id = $bookId, book name is $bookName")
        }
        cursor.close()
    }

}
