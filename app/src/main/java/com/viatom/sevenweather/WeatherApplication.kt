package com.viatom.sevenweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.litepal.LitePal

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 15:03
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 获取全局上下文
 */
class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
//        LitePal.initialize(this)
        context = applicationContext
    }

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }
}