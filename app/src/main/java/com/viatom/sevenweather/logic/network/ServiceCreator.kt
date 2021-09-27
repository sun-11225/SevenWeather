package com.viatom.sevenweather.logic.network

import com.viatom.sevenweather.utils.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 15:42
 * 邮箱：sunhao@viatomtech.com
 * 类说明: retrofit 构建器
 */
object ServiceCreator {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //泛型高级特性
    inline fun <reified T> create(): T = create(T::class.java)
}