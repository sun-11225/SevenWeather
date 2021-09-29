package com.viatom.sevenweather.ui.weather

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.viatom.sevenweather.BaseActivity
import com.viatom.sevenweather.R
import com.viatom.sevenweather.WeatherApplication
import com.viatom.sevenweather.logic.model.Weather
import com.viatom.sevenweather.logic.model.getSky
import com.viatom.sevenweather.ui.WeatherViewModel
import com.viatom.sevenweather.utils.LoggerUtils
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.forecast.*
import kotlinx.android.synthetic.main.life_index.*
import kotlinx.android.synthetic.main.now.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 16:07
 * 邮箱：sunhao@viatomtech.com
 * 类说明:
 */
class WeatherActivity : BaseActivity() {

//    private lateinit var mDataBinding: ActivityWeatherBinding

    //viewModel 懒加载
    val viewModel by lazy { ViewModelProvider(this).get(WeatherViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        //dataBinding
//        mDataBinding =
//            DataBindingUtil.setContentView<ActivityWeatherBinding>(this, R.layout.activity_weather)

        if (viewModel.locationLongitude.isEmpty()) {
            viewModel.locationLongitude = intent.getStringExtra("location_longitude") ?: ""
        }
        if (viewModel.locationLatitude.isEmpty()) {
            viewModel.locationLatitude = intent.getStringExtra("location_latitude") ?: ""
        }
        if (viewModel.placeName.isEmpty()) {
            viewModel.placeName = intent.getStringExtra("place_name") ?: ""
        }
        //观察天气数据变化
        viewModel.weatherLiveData.observe(this) { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                showWeatherInfo(weather)
            } else {
                Toast.makeText(this, "无法获取天气信息", Toast.LENGTH_SHORT).show()
                LoggerUtils.d(TAG, "无法获取天气信息")
                result.exceptionOrNull()?.printStackTrace()
            }
            swipeRefresh.isRefreshing = false
        }
        //下拉刷新天气信息
        swipeRefresh.setColorSchemeResources(R.color.design_default_color_primary)
        swipeRefresh.setOnRefreshListener {
            refreshWeather()
        }
        refreshWeather()

        //滑动菜单
        navBtn.setOnClickListener {
            //跟随系统语言自动设置滑动菜单的左滑和右滑 START
            drawerLayout.openDrawer(GravityCompat.START)
        }
        drawerLayout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

            override fun onDrawerClosed(drawerView: View) {
                //滑动菜单关闭时 ，关闭输入法
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    drawerView.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }

            override fun onDrawerStateChanged(newState: Int) {
            }
        })
    }

    /**
     * 天气信息展示
     *
     * @param weather Weather
     */
    private fun showWeatherInfo(weather: Weather) {
        val realtimeResult = weather.realtime
        val daily = weather.daily
        //填充 now.xml 布局中的数据
        placeName.text = viewModel.placeName
        val currentTempText = "${realtimeResult.temperature} ℃"
        currentTemp.text = currentTempText
        currentSky.text = getSky(realtimeResult.skyCon).info
        val currentPM25Text = "空气指数 ${realtimeResult.airQuality.aqi.chn.toInt()}"
        currentAQI.text = currentPM25Text
        nowLayout.setBackgroundResource(getSky(realtimeResult.skyCon).bg)
        // 填充forecast.xml布局中的数据
        forecastLayout.removeAllViews()
        val days = daily.skyCon.size
        for (i in 0 until days) {
            val skyCon = daily.skyCon[i]
            val temperature = daily.temperature[i]
            //给forecastLayout增加子布局
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, forecastLayout, false)
            val dateInfo = view.findViewById(R.id.dateInfo) as TextView
            val skyIcon = view.findViewById(R.id.skyIcon) as ImageView
            val skyInfo = view.findViewById(R.id.skyInfo) as TextView
            val temperatureInfo = view.findViewById(R.id.temperatureInfo) as TextView
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skyCon.date)
            val sky = getSky(skyCon.value)
            skyIcon.setImageResource(sky.icon)
            skyInfo.text = sky.info
            val tempText = "${temperature.min.toInt()} ~ ${temperature.max.toInt()} ℃"
            temperatureInfo.text = tempText
            forecastLayout.addView(view)
        }
        // 填充life_index.xml布局中的数据
        val lifeIndex = daily.lifeIndex
        coldRiskText.text = lifeIndex.coldRisk[0].desc
        dressingText.text = lifeIndex.dressing[0].desc
        ultravioletText.text = lifeIndex.ultraviolet[0].desc
        carWashingText.text = lifeIndex.carWashing[0].desc
        weatherLayout.visibility = View.VISIBLE
    }

    /**
     * 刷新天气信息
     */
     fun refreshWeather() {
        viewModel.refreshWeather(viewModel.locationLongitude, viewModel.locationLatitude)
        Toast.makeText(WeatherApplication.context, "刷新成功", Toast.LENGTH_SHORT).show()
        swipeRefresh.isRefreshing = true
    }

    companion object {
        private const val TAG = "WeatherActivity"
    }

}