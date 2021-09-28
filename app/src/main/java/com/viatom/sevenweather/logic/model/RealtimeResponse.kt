package com.viatom.sevenweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 14:18
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 实时天气信息数据类
 */
data class RealtimeResponse(val status: String, val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        @SerializedName("skycon")val skyCon: String,
        val temperature: Int,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}
