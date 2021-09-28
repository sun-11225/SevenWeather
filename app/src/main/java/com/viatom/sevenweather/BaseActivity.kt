package com.viatom.sevenweather

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.viatom.sevenweather.utils.ActivityCollector
import com.viatom.sevenweather.utils.TIME_EXIT

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 10:13
 * 邮箱：sunhao@viatomtech.com
 * 类说明: activity 基类
 */
open class BaseActivity : AppCompatActivity() {

     private var mBackTime : Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        /**状态栏适配 */
        //拿到当前activity的decorView
        val decorView =window.decorView
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    /**
     * 双击退出程序
     */
    override fun onBackPressed() {
        if (mBackTime + TIME_EXIT > System.currentTimeMillis()){
//            super.onBackPressed()
            ActivityCollector.appExit()
        }else{
            Toast.makeText(this,getString(R.string.double_exit),Toast.LENGTH_SHORT).show()
            mBackTime=System.currentTimeMillis();
        }
    }
}