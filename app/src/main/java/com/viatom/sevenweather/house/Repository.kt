package com.viatom.sevenweather.house

import androidx.lifecycle.liveData
import com.viatom.sevenweather.logic.dao.PlaceDao
import com.viatom.sevenweather.logic.model.Place
import com.viatom.sevenweather.logic.model.Weather
import com.viatom.sevenweather.logic.network.WeatherNetwork
import com.viatom.sevenweather.utils.LoggerUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 16:43
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 仓库类
 */
object Repository {

    private const val TAG = "Repository"


    /**
     * 获取全球城市天气信息数据并返回
     * liveData 函数 它可以自动构建并返回一个LiveData对象，然后在它的代码块中提供一个挂起函数的上下文，所以可以在liveData()函数的代码块中调用任意的挂起函数了
     * 开启子线程执行网络请求
     *
     * @param query String
     * @return LiveData<Result<List<Place>>>
     */
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
            LoggerUtils.d(TAG, e.message)
            Result.failure(e)
        }
        //类似 liveData的setValue()通知数据变化
        emit(result)
    }


    /**
     * 获取实时天气信息和未来几日天气信息数据，封装成一个weather对象返回
     *
     * @param longitude String
     * @param latitude String
     * @return LiveData<Result<Weather>>
     */
    fun refreshWeather(longitude: String, latitude: String) = liveData(Dispatchers.IO) {
        val result = try {
            coroutineScope {
                val deferredRealtimeWeather =
                    async { WeatherNetwork.getRealtimeWeather(longitude, latitude) }
                val deferredDailyWeather =
                    async { WeatherNetwork.getDailyWeather(longitude, latitude) }
                val realtimeResponse = deferredRealtimeWeather.await()
                val dailyResponse = deferredDailyWeather.await()

                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                    val weather =
                        Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                    Result.success(weather)
                } else {
                    Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}" + "realtime response status is ${dailyResponse.status}"))
                }

            }
        } catch (e: Exception) {
            LoggerUtils.d(TAG, e.message)
            Result.failure(e)
        }
        emit(result)
    }

    /**
     * refreshWeather函数简化try catch 后的写法
     *
     * @param longitude String
     * @param latitude String
     * @return LiveData<Result<Weather>>
     */
    fun optimizeRefreshWeather(longitude: String, latitude: String) = optimize(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtimeWeather =
                async { WeatherNetwork.getRealtimeWeather(longitude, latitude) }
            val deferredDailyWeather =
                async { WeatherNetwork.getDailyWeather(longitude, latitude) }
            val realtimeResponse = deferredRealtimeWeather.await()
            val dailyResponse = deferredDailyWeather.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(RuntimeException("realtime response status is ${realtimeResponse.status}" + "realtime response status is ${dailyResponse.status}"))
            }
        }
    }

    /**
     * 代码终极优化 简化每次进行网络请求时需要进行try catch
     *
     * @param context CoroutineContext
     * @param block SuspendFunction0<Result<T>>
     * @return LiveData<Result<T>>
     */
    private fun <T> optimize(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure(e)
            }
            emit(result)
        }

    /**
     * 保存选中的城市
     *
     * @param place Place
     */
    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    /**
     * 获取保存的城市
     *
     * @return Place
     */
    fun getSavedPlace() = PlaceDao.getSavedPlace()

    /**
     * 判断是否储存有城市数据
     *
     * @return Boolean
     */
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

}