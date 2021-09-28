package com.viatom.sevenweather.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 14:25
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 未来天气数据类
 */
data class DailyResponse(val status: String, val result: Result) {

    data class Result(val daily: Daily)

    class Daily(
        val temperature: List<Temperature>,
        @SerializedName("skycon") val skyCon: List<SkyCon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    class Temperature(val max: Float, val min: Float)

    class SkyCon(val value: String, val date: Date)

    class LifeIndex(
        val coldRisk: List<LifeDescription>,
        val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>,
        val dressing: List<LifeDescription>
    )

    class LifeDescription(val desc: String)

}
