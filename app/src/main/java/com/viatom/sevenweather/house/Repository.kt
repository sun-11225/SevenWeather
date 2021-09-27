package com.viatom.sevenweather.house

import androidx.lifecycle.liveData
import com.viatom.sevenweather.logic.model.Place
import com.viatom.sevenweather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 16:43
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 仓库类
 */
object Repository {

    //liveData 函数 它可以自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，所以可以在liveData()函数的代码块中调用任意的挂起函数了
    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = WeatherNetwork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}