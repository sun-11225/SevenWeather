package com.viatom.sevenweather.logic.network

import com.viatom.sevenweather.logic.model.DailyResponse
import com.viatom.sevenweather.logic.model.RealtimeResponse
import com.viatom.sevenweather.utils.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 14:44
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 访问天气信息数据的retrofit接口
 */
interface WeatherService {

    /**
     * 获取实时天气信息
     *
     * @param longitude String
     * @param latitude String
     * @return Call<RealtimeResponse>
     */
    @GET("v2.5/${TOKEN}/{longitude},{latitude}/realtime.json")
    fun getRealtimeWeather(@Path("longitude") longitude: String, @Path("latitude") latitude: String) : Call<RealtimeResponse>

    /**
     * 获取未来几日天气信息
     *
     * @param longitude String
     * @param latitude String
     * @return Call<DailyResponse>
     */
    @GET("v2.5/${TOKEN}/{longitude},{latitude}/daily.json")
    fun getDailyWeather(@Path("longitude") longitude: String, @Path("latitude") latitude: String) : Call<DailyResponse>

}