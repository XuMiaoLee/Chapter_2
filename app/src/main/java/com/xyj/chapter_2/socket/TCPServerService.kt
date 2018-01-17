package com.xyj.chapter_2.socket

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.xyj.chapter_2.MyUtils
import com.xyj.chapter_2.utils.LogUtils
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.ServerSocket
import java.util.*

class TCPServerService : Service() {

    private var mIsServerDestroy: Boolean = false
    private val mDefinedMessage = arrayOf("你好啊，哈哈",
            "请问你叫什么名字?",
            "今天南京天气不错呀，很皮",
            "呀呀呀呀哎呀！")

    override fun onBind(intent: Intent): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        Thread(TCPServer()).start()
    }


    inner class TCPServer : Runnable {
        override fun run() {
            //监听本地8688端口的连接
            val serverSocket = ServerSocket(8688)
            LogUtils.d("开始监听")
            while (!mIsServerDestroy) {
                val client = serverSocket.accept()
                LogUtils.d("accept")
                Thread {
                    //接收消息
                    val input = BufferedReader(InputStreamReader(client.getInputStream()))
                    //给客户端发送消息
                    val output = PrintWriter(OutputStreamWriter(client.getOutputStream()),true)
                    output.println("欢迎来到聊天室！")
                    while (!mIsServerDestroy) {
                        //断开连接
                        val readLine = input.readLine() ?: break
                        LogUtils.d("server receive message from client: $readLine")

                        val sendMsg = mDefinedMessage[Random().nextInt(mDefinedMessage.size)]
                        LogUtils.d("server send message to client: $sendMsg")
                        output.println(sendMsg)
                    }
                    //关闭流和客户端socket
                    MyUtils.close(output)
                    MyUtils.close(input)
                    client.close()
                }.start()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mIsServerDestroy = true
    }
}
