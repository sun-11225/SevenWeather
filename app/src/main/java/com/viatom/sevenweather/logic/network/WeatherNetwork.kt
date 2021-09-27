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

    //请求数据 挂起函数：在挂起函数或协程作用域中调用
    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
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