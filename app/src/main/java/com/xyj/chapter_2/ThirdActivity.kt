package com.xyj.chapter_2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.xyj.chapter_2.manager.UserManager
import com.xyj.chapter_2.utils.LogUtils

class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third_acitivity)
        LogUtils.d("sUserId=" + UserManager.sUserId)
    }
}
