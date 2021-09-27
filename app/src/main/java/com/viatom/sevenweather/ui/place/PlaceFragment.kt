package com.viatom.sevenweather.ui.place

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
import androidx.recyclerview.widget.RecyclerView
import com.viatom.sevenweather.R
import com.viatom.sevenweather.WeatherApplication
import com.viatom.sevenweather.databinding.FragmentPlaceBinding
import com.viatom.sevenweather.ui.PlaceViewModel

/**
 * @author：created by sunhao
 * 创建时间：2021/9/27 18:00
 * 邮箱：sunhao@viatomtech.com
 * 类说明: 城市数据显示碎片
 */
class PlaceFragment : Fragment() {

    //懒加载
    private val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    //天气数据适配器
    lateinit var adapter: PlaceAdapter
    private lateinit var fragmentPlaceBinding: FragmentPlaceBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentPlaceBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_place, container, false)
        return fragmentPlaceBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        val recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
        //设置适配器
        fragmentPlaceBinding.recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = PlaceAdapter(this, viewModel.placeList)
        fragmentPlaceBinding.recyclerView.adapter = adapter
        //edit 数据变化监听
        fragmentPlaceBinding.searchPlaceEdit.addTextChangedListener { editable ->
            val content = editable.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                fragmentPlaceBinding.recyclerView.visibility = View.GONE
                fragmentPlaceBinding.bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }

        //监听数据变化
        viewModel.placeLiveData.observe(viewLifecycleOwner) { result ->
            val places = result.getOrNull()
            if (places != null) {
                fragmentPlaceBinding.recyclerView.visibility = View.VISIBLE
                fragmentPlaceBinding.bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未查询到地点，请重试", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }


    }
}