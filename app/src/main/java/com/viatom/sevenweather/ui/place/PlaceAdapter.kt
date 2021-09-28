package com.viatom.sevenweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viatom.sevenweather.R
import com.viatom.sevenweather.WeatherApplication
import com.viatom.sevenweather.logic.model.Place
import com.viatom.sevenweather.ui.weather.WeatherActivity
import com.viatom.sevenweather.utils.LogUtils
import com.viatom.sevenweather.utils.LoggerUtils

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 17:37
 * 邮箱：sunhao@viatomtech.com
 * 类说明: recyclerview adapter
 */
class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    /**
     * 子项数据缓存类
     *
     * @property placeName TextView
     * @property placeAddress TextView
     * @constructor
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            val position = holder.bindingAdapterPosition
            LoggerUtils.d(TAG,"$position")
            val place = placeList[position]
            //intent 跳转界面,传递数据
            val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                putExtra("location_longitude", place.location.lng)
                putExtra("location_latitude", place.location.lat)
                putExtra("place_name", place.name)
            }
            //测试打印
            LoggerUtils.d(
                TAG,
                "location_longitude: + ${place.location.lng} + location_latitude:  ${place.location.lat} + place_name: ${place.name}"
            )
            fragment.viewModel.savePlace(place)
            fragment.startActivity(intent)
            fragment.activity?.finish()

        }
        return holder
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
        holder.placeAddress.text = place.address
    }

    /**
     * 返回子项数量
     *
     * @return Int
     */
    override fun getItemCount(): Int {
        return placeList.size
    }

    companion object {
        private const val TAG = "PlaceAdapter"
    }
}