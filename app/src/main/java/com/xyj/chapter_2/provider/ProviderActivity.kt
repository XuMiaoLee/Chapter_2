package com.xyj.chapter_2.provider

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.R

class ProviderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider)

        val uri = Uri.parse("content://com.xyj.chapter_2.book.provider")
        contentResolver.query(uri, null, null, null, null)
        contentResolver.query(uri, null, null, null, null)
        contentResolver.query(uri, null, null, null, null)
    }
}
