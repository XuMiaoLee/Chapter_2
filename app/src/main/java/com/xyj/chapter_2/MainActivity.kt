package com.xyj.chapter_2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.Messenger
import android.util.Log
import android.view.View
import com.xyj.chapter_2.manager.UserManager
import com.xyj.chapter_2.model.User
import com.xyj.chapter_2.utils.LogUtils
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        UserManager.sUserId = 2
        LogUtils.d("sUserId=" + UserManager.sUserId)

        writeObjectToFile()
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

    /**
     * 多进程文件共享
     */
    private fun writeObjectToFile() {
        Thread() {
            val user = User("许渺", 21)
            var oos: ObjectOutputStream? = null
            if (Environment.getExternalStorageState() != Environment.MEDIA_MOUNTED) {
                LogUtils.d("SD卡不存在")
                return@Thread
            }
            val cacheFile = File(MyContants.getInternalStorageDir(this))
            try {
                oos = ObjectOutputStream(FileOutputStream(cacheFile))
                oos.writeObject(user)
            } catch (e: Exception) {
            } finally {
                MyUtils.close(oos)
            }
        }.start()
    }
}
