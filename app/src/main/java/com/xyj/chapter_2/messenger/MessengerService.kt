package com.xyj.chapter_2.messenger

import android.app.Service
import android.content.Intent
import android.os.*
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

                    //回复客户端
                    val client = msg.replyTo
                    val replyMsg = Message.obtain()
                    val bundle = Bundle()
                    bundle.putString("reply", "收到消息")
                    replyMsg.data = bundle
                    client.send(replyMsg)

                }
            }
            super.handleMessage(msg)
        }
    }
}
