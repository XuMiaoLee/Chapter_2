package com.xyj.chapter_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.xyj.chapter_2.manager.UserManager
import com.xyj.chapter_2.utils.LogUtils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        UserManager.sUserId = 2
        LogUtils.d("sUserId=" + UserManager.sUserId)
    }

    fun secondClick(view: View) {
        startActivity(Intent(this, SecondActivity::class.java))
    }

    fun thirdClick(view: View) {
        startActivity(Intent(this, ThirdActivity::class.java))
    }

    companion object {

        val TAG = MainActivity::class.java.simpleName
    }
}
