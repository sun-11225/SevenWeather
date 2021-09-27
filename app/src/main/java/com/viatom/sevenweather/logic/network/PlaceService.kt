package com.viatom.sevenweather.logic.network

import com.viatom.sevenweather.logic.model.PlaceResponse
import com.viatom.sevenweather.utils.TOKEN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 15:24
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 访问天气数据的retrofit接口
 */
interface PlaceService {

    //服务器请求数据，响应的数据自动解析成PlaceResponse对象
    @GET("v2/place?token=${TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String) :Call<PlaceResponse>

}