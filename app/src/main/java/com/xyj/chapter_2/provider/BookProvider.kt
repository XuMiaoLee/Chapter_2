package com.xyj.chapter_2.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.support.annotation.Nullable
import com.xyj.chapter_2.utils.LogUtils

/*
 * 包名       com.xyj.chapter_2.provider
 * 文件名:    BookProvider
 * 创建者:    xuyj
 * 创建时间:  2018/1/16 on 14:04
 * 描述:     TODO
 */

class BookProvider : ContentProvider() {


    companion object {
        /**
         * 定义静态常量
         * java：public static final AUTHORITY = "com.xyj.chapter_2.book.provider";
         */
        const val AUTHORITY = "com.xyj.chapter_2.book.provider"
        const val BOOK_CONTENT_URI = "content://$AUTHORITY/book"
        const val USER_CONTENT_URI = "content://$AUTHORITY/user"
        const val BOOK_URI_CODE = 0
        const val USER_URI_CODE = 1
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        //静态代码块
        init {
            sUriMatcher.addURI(AUTHORITY, "book", BOOK_URI_CODE)
            sUriMatcher.addURI(AUTHORITY, "user", USER_URI_CODE)
        }
    }

    private lateinit var mDB: SQLiteDatabase

    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        LogUtils.d("insert")
        val tableName = getTableName(uri!!) ?: throw IllegalArgumentException("Unsupported uri : $uri")
        mDB.insert(tableName, null, values)
        //插入成功后要更新
        context.contentResolver.notifyChange(uri, null)
        return uri
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? {
        LogUtils.d("query,current thread : ${Thread.currentThread().name}")
        val tableName = getTableName(uri!!) ?: throw IllegalArgumentException("Unsupported uri : $uri")

        return mDB.query(tableName, projection, selection, selectionArgs, null, null, sortOrder, null)
    }

    override fun onCreate(): Boolean {
        LogUtils.d("onCreate,current thread : ${Thread.currentThread().name}")
        initProviderData()
        return true
    }

    /**
     * 初始化数据库数据
     */
    private fun initProviderData() {
        mDB = DbOpenHelper(context).writableDatabase
        mDB.execSQL("DELETE FROM ${DbOpenHelper.BOOK_TABLE_NAME}")
        mDB.execSQL("DELETE FROM ${DbOpenHelper.USER_TABLE_NAME}")
        mDB.execSQL("INSERT INTO ${DbOpenHelper.BOOK_TABLE_NAME} VALUES (1,'Android')")
        mDB.execSQL("INSERT INTO ${DbOpenHelper.BOOK_TABLE_NAME} VALUES (2,'iOS')")
        mDB.execSQL("INSERT INTO ${DbOpenHelper.BOOK_TABLE_NAME} VALUES (3,'HTML5')")

        mDB.execSQL("INSERT INTO ${DbOpenHelper.USER_TABLE_NAME} VALUES (1,'jake',0)")
        mDB.execSQL("INSERT INTO ${DbOpenHelper.USER_TABLE_NAME} VALUES (2,'tom'),1")
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        LogUtils.d("update")
        val tableName = getTableName(uri!!) ?: throw IllegalArgumentException("Unsupported uri : $uri")
        mDB.update(tableName, values, selection, selectionArgs)
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        LogUtils.d("delete")
        val tableName = getTableName(uri!!) ?: throw IllegalArgumentException("Unsupported uri : $uri")
        mDB.delete(tableName, selection, selectionArgs)
        return 0
    }

    override fun getType(uri: Uri?): String? {
        LogUtils.d("getType")
        return null
    }

    private fun getTableName(@Nullable uri: Uri): String? = when (sUriMatcher.match(uri)) {
        BOOK_URI_CODE -> DbOpenHelper.BOOK_TABLE_NAME
        USER_URI_CODE -> DbOpenHelper.USER_TABLE_NAME
        else -> null
    }

}
