package cn.dengxijian.demo.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import cn.dengxijian.demo.CallBack.MyItemTouchCallBack
import cn.dengxijian.demo.Listenner.OnStartDragListener
import cn.dengxijian.demo.Adapter.RecycleViewAdapter
import cn.dengxijian.demo.Divider.TextItemDecoration
import cn.dengxijian.demo.R
import kotlinx.android.synthetic.main.recycleview.*

class MainActivity : AppCompatActivity(), OnStartDragListener {
    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemHelper.startDrag(viewHolder)
    }


    lateinit var adapter: RecycleViewAdapter
    var dataList = ArrayList<String>()
    lateinit var itemHelper :ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycleview)
        initView()
    }

    fun initView() {

        initData()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = RecycleViewAdapter(this, this, dataList)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(TextItemDecoration())
        itemHelper = ItemTouchHelper(MyItemTouchCallBack(adapter))
        itemHelper.attachToRecyclerView(recyclerView)
    }

    fun initData() {
        for(i in 0 ..100){
            dataList.add("item $i")
        }
    }
}
