package com.xyj.chapter_2.messenger

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.support.v7.app.AppCompatActivity
import com.xyj.chapter_2.MyContants
import com.xyj.chapter_2.R
import com.xyj.chapter_2.model.User
import com.xyj.chapter_2.utils.LogUtils

class MessengerActivity : AppCompatActivity(), ServiceConnection {

    private var mService: Messenger? = null
    /**
     * 接受服务端返回的消息
     */
    private val mGetReplyMessenger = Messenger(MessengerHandler())

    class MessengerHandler : Handler() {
        override fun handleMessage(msg: Message?) {
            val replyMsg = msg?.data?.getString("reply")
            LogUtils.d(replyMsg!!)
        }
    }

    override fun onServiceDisconnected(name: ComponentName?) {

    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        mService = Messenger(service)
        val msg = Message.obtain(null, MyContants.MSG_FROM_CLIENT)
        //设置msg的replyTo，这样服务端就可以就行消息回复
        msg.replyTo = mGetReplyMessenger
        val bundle = Bundle()
        bundle.putString("msg", "hello ,this is client!")
        msg.data = bundle
//        msg.obj = User("须弥哦啊",1)
        try {
            mService?.send(msg)
        } catch (e: RemoteException) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)

        val intent = Intent(this, MessengerService::class.java)
        bindService(intent, this, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(this)
    }

}
