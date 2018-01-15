package com.xyj.chapter_2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.manager.UserManager
import com.xyj.chapter_2.utils.LogUtils
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        LogUtils.d("sUserId=" + UserManager.sUserId)
        readObjectToFile()
    }

    companion object {

        val TAG: String = SecondActivity::class.java.simpleName
    }

    private fun readObjectToFile() = Thread() {
        val cacheFile = File(MyContants.getInternalStorageDir(this))
        if (cacheFile.exists()) {
            var ois: ObjectInputStream? = null
            try {
                ois = ObjectInputStream(FileInputStream(cacheFile))
                val user = ois.readObject()
                LogUtils.d(user.toString())
            } catch (e: Exception) {
            } finally {
                MyUtils.close(ois)
            }
        }
    }.start()
}
