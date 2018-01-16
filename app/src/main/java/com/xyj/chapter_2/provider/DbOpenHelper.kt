package com.xyj.chapter_2.provider

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/*
 * 包名       com.xyj.chapter_2.provider
 * 文件名:    DbOpenHelper
 * 创建者:    xuyj
 * 创建时间:  2018/1/16 on 15:45
 * 描述:     TODO
 */

class DbOpenHelper(var ctx: Context) : SQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "db_provider.db"
        const val DB_VERSION = 1
        const val BOOK_TABLE_NAME = "book"
        const val USER_TABLE_NAME = "user"
    }

    private val CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS $BOOK_TABLE_NAME (_id INTEGER PRIMARY KEY,name TEXT)"
    private val CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS $USER_TABLE_NAME (_id INTEGER PRIMARY KEY,name TEXT,sex INT"


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_BOOK_TABLE)
        db?.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

}
