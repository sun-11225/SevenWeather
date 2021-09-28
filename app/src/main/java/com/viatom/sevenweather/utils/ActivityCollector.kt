package com.viatom.sevenweather.utils

import android.app.Activity
import android.os.Process
import java.util.*
import kotlin.system.exitProcess

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 10:03
 * 邮箱：sunhao@viatomtech.com
 * 类说明: Activity 管理类
 */
object ActivityCollector {

    private val activityStack = Stack<Activity>()

    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activityStack.remove(activity)
    }

    private fun finishAll() {
        for (activity in activityStack) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activityStack.clear()
    }

    /**
     * 退出 app
     */
    fun appExit() {
        finishAll()
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

}