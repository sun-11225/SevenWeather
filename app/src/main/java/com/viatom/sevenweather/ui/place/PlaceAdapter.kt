package com.viatom.sevenweather.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viatom.sevenweather.R
import com.viatom.sevenweather.logic.model.Place

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 17:37
 * 邮箱：sunhao@viatomtech.com
 * 类说明: recyclerview adapter
 */
class PlaceAdapter(private val fragment: PlaceFragment ,private val placeList: List<Place>) : RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    /**
     * 子项数据缓存类
     *
     * @property placeName TextView
     * @property placeAddress TextView
     * @constructor
     */
    inner class ViewHolder (view: View) : RecyclerView.ViewHolder(view){
         val placeName: TextView = view.findViewById(R.id.placeName)
         val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    /**
     * 加载子项布局
     *
     * @param parent ViewGroup
     * @param viewType Int
     * @return PlaceAdapter.ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,parent,false)
        return ViewHolder(view)
    }

    /**
     * 数据绑定
     *
     * @param holder ViewHolder
     * @param position Int
     */
    override fun onBindViewHolder(holder: PlaceAdapter.ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.formatted_address
    }

    /**
     * 返回子项数量
     *
     * @return Int
     */
    override fun getItemCount(): Int {
        return placeList.size
    }
}