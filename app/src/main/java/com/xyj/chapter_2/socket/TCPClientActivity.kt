package com.xyj.chapter_2.socket

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.xyj.chapter_2.MyUtils
import com.xyj.chapter_2.R
import com.xyj.chapter_2.R.id.msg_container
import com.xyj.chapter_2.utils.LogUtils
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.text.SimpleDateFormat
import java.util.*

class TCPClientActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mSendButton: Button
    private lateinit var mMessageTextView: TextView
    private lateinit var mMessageEditText: EditText

    private lateinit var mClientSocket: Socket
    private lateinit var mPrintWriter: PrintWriter

    companion object {
        const val MESSAGE_SOCKET_CONNECTED = 1
        const val MESSAGE_RECEIVE_NEW_MSG = 2
        const val MESSAGE_SEND_NEW_MSG = 3
    }

    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                MESSAGE_SOCKET_CONNECTED -> mSendButton.isEnabled = true
                MESSAGE_RECEIVE_NEW_MSG -> mMessageTextView.setText(mMessageTextView.text.toString() + msg.obj)
                MESSAGE_SEND_NEW_MSG -> {
                    mMessageEditText.setText("")

                    val time = formatDateTime(System.currentTimeMillis())
                    val showMsg = "client($time):${msg.obj}"
                    mMessageTextView.setText(mMessageTextView.text.toString() + showMsg + "\n")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tcpclient)

        mSendButton = findViewById(R.id.send) as Button
        mMessageEditText = findViewById(R.id.msg_content) as EditText
        mMessageTextView = findViewById(msg_container) as TextView
        mSendButton.setOnClickListener(this)

        //start service
        val intent = Intent(this, TCPServerService::class.java)
        startService(intent)

        //connection server
        Thread {
            Thread.sleep(1000)
            connectTCPServer()
        }.start()
    }

    private fun connectTCPServer() {
        var socket: Socket? = null
        while (socket == null) {
            LogUtils.d("开始连接")
            socket = Socket("localhost", 8688)
            mClientSocket = socket
            mPrintWriter = PrintWriter(BufferedOutputStream(socket.getOutputStream()), true)
            mHandler.sendEmptyMessage(MESSAGE_SOCKET_CONNECTED)
        }

        //接收服务端消息
        val input = BufferedReader(InputStreamReader(socket?.getInputStream()))
        while (!isFinishing) {
            val msg = input.readLine()
            if (msg != null) {
                val time = formatDateTime(System.currentTimeMillis())
                val showMsg = "server($time):$msg \n"
                mHandler.obtainMessage(MESSAGE_RECEIVE_NEW_MSG, showMsg).sendToTarget()
            }
        }

        MyUtils.close(input)
        MyUtils.close(mPrintWriter)
        socket?.close()
    }

    override fun onClick(v: View?) {
        val msg = mMessageEditText.text.toString()
        if (!TextUtils.isEmpty(msg) && mPrintWriter != null) {
            Thread {
                //该方法属于网络操作
                mPrintWriter.println(msg)
                mHandler.obtainMessage(MESSAGE_SEND_NEW_MSG, msg).sendToTarget()
            }.start()
        }
    }

    private fun formatDateTime(time: Long): String = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(time))

    override fun onDestroy() {
        super.onDestroy()
        if (mClientSocket != null) {
            mClientSocket.shutdownInput()
            mClientSocket.close()
        }
    }
}
