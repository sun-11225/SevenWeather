package com.viatom.sevenweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 16:00
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 网络数据源访问入口
 */
object WeatherNetwork {

    //获取PlaceService接口的动态代理对象
    private val placeService = ServiceCreator.create(PlaceService::class.java)

    //获取WeatherService接口的动态代理对象
    private val weatherService = ServiceCreator.create<WeatherService>()

    //根据输入字符串请求全球城市数据 挂起函数：在挂起函数或协程作用域中调用
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    //根据经纬度请求实时天气信息数据
    suspend fun getRealtimeWeather(longitude: String, latitude: String) =
        weatherService.getRealtimeWeather(longitude, latitude).await()

    //请求未来几日天气信息数据
    suspend fun getDailyWeather(longitude: String, latitude: String) =
        weatherService.getDailyWeather(longitude, latitude).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    //返回值
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("response body is null"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }
            })
        }
    }
}