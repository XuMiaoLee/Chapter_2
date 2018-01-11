package com.xyj.chapter_2

import android.content.Context
import android.os.Environment
import java.io.File

/**
 * Created by xuyongjun on 2018/1/11.
 */

object MyContants {

    fun getInternalStorageDir(ctx: Context): String = "${ctx.cacheDir}${File.separator}chapter_2_cache.txt"

    const val MSG_FROM_CLIENT = 0
    const val MSG_FROM_SERVICE = 1
}
