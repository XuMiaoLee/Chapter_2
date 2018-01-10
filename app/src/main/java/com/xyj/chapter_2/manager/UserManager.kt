package com.xyj.chapter_2.manager

/**
 * Created by xuyongjun on 2017/9/14.
 */

class UserManager {

    companion object {
        /**
         * 多进程下内存不共享
         */
        var sUserId = 1;
    }

}
