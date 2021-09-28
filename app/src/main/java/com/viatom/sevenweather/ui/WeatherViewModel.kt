package com.viatom.sevenweather.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.viatom.sevenweather.house.Repository
import com.viatom.sevenweather.logic.model.Location

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 15:44
 * 邮箱：sunhao@viatomtech.com
 * 类说明: Weather ViewModel
 */
class WeatherViewModel : ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    //和界面有关的数据都应该放在ViewModel中
    var locationLongitude = ""
    var locationLatitude = ""
    var placeName = ""


    //如果ViewModel中的某个LiveData对象是调用另外的方法获取的，借助switchMap()方法，将这个LiveData对象转换成另外一个可观察的LiveData对象。
    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.optimizeRefreshWeather(location.lng, location.lat)
    }

    /**
     * 查询天气信息
     *
     * @param longitude String
     * @param latitude String
     */
    fun refreshWeather(longitude: String, latitude: String) {
        locationLiveData.value = Location(longitude, latitude)
    }
}