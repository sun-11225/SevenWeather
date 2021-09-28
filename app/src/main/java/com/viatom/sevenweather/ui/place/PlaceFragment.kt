package com.viatom.sevenweather.ui.place

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.viatom.sevenweather.MainActivity
import com.viatom.sevenweather.R
import com.viatom.sevenweather.WeatherApplication
//import com.viatom.sevenweather.databinding.FragmentPlaceBinding
import com.viatom.sevenweather.ui.PlaceViewModel
import com.viatom.sevenweather.ui.weather.WeatherActivity
import com.viatom.sevenweather.utils.LoggerUtils
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @author：created by sun hao
 * 创建时间：2021/9/27 18:00
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 城市数据显示碎片
 */
class PlaceFragment : Fragment() {

    //懒加载
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    //天气数据适配器
    private lateinit var adapter: PlaceAdapter
//    private lateinit var fragmentPlaceBinding: FragmentPlaceBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        fragmentPlaceBinding =
//            DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false)
//        return fragmentPlaceBinding.root
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
//        记录选中的城市数据 weatherActivity 和 mainActivity 都加载了 该fragment 使用 activity is MainActivity 区分 类似java的 instanceof
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()
            val intent = Intent(context, WeatherActivity::class.java).apply {
                putExtra("location_longitude", place.location.lng)
                putExtra("location_latitude", place.location.lat)
                putExtra("place_name", place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        //设置适配器
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        //edit 数据变化监听
        searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyItemRangeChanged(0, 5)
//                adapter.notifyDataSetChanged()
            }
        }

        //观察数据变化
        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
//                adapter.notifyDataSetChanged()
                LoggerUtils.d(TAG, places[0].location.toString())
                adapter.notifyItemRangeChanged(0, 5)
            } else {
                Toast.makeText(WeatherApplication.context, "未查询到指定地点，请重试", Toast.LENGTH_SHORT)
                    .show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }


    }

    companion object {
        private const val TAG = "PlaceFragment"
    }
}