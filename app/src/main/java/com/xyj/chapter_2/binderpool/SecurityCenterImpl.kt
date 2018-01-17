package com.xyj.chapter_2.binderpool

/*
 * 包名       com.xyj.chapter_2.binderpool
 * 文件名:    SecurityCenterImpl
 * 创建者:    xuyj
 * 创建时间:  2018/1/17 on 15:38
 * 描述:     TODO
 */

import android.os.RemoteException

class SecurityCenterImpl : ISecurityCenter.Stub() {

    /**
     * 这边加解密是用的异或的方式来进行的
     *
     * @param content
     * @return
     * @throws RemoteException
     */
    @Throws(RemoteException::class)
    override fun encrypt(content: String): String {
        val chars = content.toCharArray()
        for (i in chars.indices) {
            chars[i] = ((chars[i].toInt()) xor (SECRET_CODE.toInt())).toChar()
        }
        return String(chars)
    }

    @Throws(RemoteException::class)
    override fun decrypt(password: String): String = encrypt(password)

    companion object {
        private val SECRET_CODE = '^'
    }
}
