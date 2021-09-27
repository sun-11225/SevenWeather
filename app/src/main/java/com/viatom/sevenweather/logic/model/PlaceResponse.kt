package com.viatom.sevenweather.logic.model

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 15:12
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 全球城市数据类
 */
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(val name :String, val location :Location, val formatted_address : String)

data class Location(val latitude : Int, val longitude : Int)
