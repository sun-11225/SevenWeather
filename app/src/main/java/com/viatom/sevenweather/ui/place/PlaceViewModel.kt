package com.viatom.sevenweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.viatom.sevenweather.house.Repository
import com.viatom.sevenweather.logic.dao.PlaceDao
import com.viatom.sevenweather.logic.model.Place
import java.util.*

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 17:03
 * 邮箱：sunhao@viatomtech.com
 * 类说明:  Place ViewModel
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    //缓存城市数据
    val placeList = LinkedList<Place>()

    //如果ViewModel中的某个LiveData对象是调用另外的方法获取的，借助switchMap()方法，将这个LiveData对象转换成另外一个可观察的LiveData对象。
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    /**
     * 查询城市信息
     *
     * @param query String
     */
    fun searchPlaces(query: String) {
        searchLiveData.value = query
    }

    /**
     * 保存选中的城市
     *
     * @param place Place
     */
    fun savePlace(place: Place) = Repository.savePlace(place)

    /**
     * 获取保存的城市
     *
     * @return Place
     */
    fun getSavedPlace() = Repository.getSavedPlace()

    /**
     * 判断是否储存有城市数据
     *
     * @return Boolean
     */
    fun isPlaceSaved() = Repository.isPlaceSaved()
}