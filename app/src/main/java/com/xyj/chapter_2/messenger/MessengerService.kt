package com.xyj.chapter_2.messenger

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.xyj.chapter_2.MyContants
import com.xyj.chapter_2.utils.LogUtils

class MessengerService : Service() {

    private val mMessenger = Messenger(MessengerHandler())

    override fun onBind(intent: Intent): IBinder? = mMessenger.binder

    class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MyContants.MSG_FROM_CLIENT -> {
                    LogUtils.d("receive msg from Client: ${msg.data.get("msg")}")
//                    LogUtils.d(msg.obj.toString())
                }
            }
            super.handleMessage(msg)
        }
    }
}
