package com.viatom.sevenweather.logic.model

/**
 * @author：created by sunhao
 * 创建时间：2021/9/28 14:37
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 天气信息封装类
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)
