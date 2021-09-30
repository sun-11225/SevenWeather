package com.viatom.sevenweather.logic.dao

import android.content.Context
import com.google.gson.Gson
import com.viatom.sevenweather.WeatherApplication
import com.viatom.sevenweather.logic.model.Place
import com.viatom.sevenweather.utils.*


/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 18:52
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 储存选中的城市
 */
object PlaceDao {

    /**
     * SharedPreferences create
     *
     * @return (SharedPreferences..SharedPreferences?)
     */
    private fun sharedPreferences() =
        WeatherApplication.context.getSharedPreferences("weather", Context.MODE_PRIVATE)

    /**
     * 保存选中的城市
     *
     * @param place Place
     */
    fun savePlace(place: Place) {
        sharedPreferences().edit().putString(SAVE_PLACE, Gson().toJson(place)).apply()
    }

    /**
     * 获取保存的城市
     *
     * @return Place
     */
    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString(SAVE_PLACE, "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    /**
     * 判断是否储存有城市数据
     *
     * @return Boolean
     */
    fun isPlaceSaved() = sharedPreferences().contains(SAVE_PLACE)
}