package cn.dengxijian.demo.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import cn.dengxijian.demo.CallBack.MyItemTouchCallBack
import cn.dengxijian.demo.Listenner.OnStartDragListener
import cn.dengxijian.demo.Adapter.RecycleViewAdapter
import cn.dengxijian.demo.Divider.TextItemDecoration
import kotlinx.android.synthetic.main.recycleview.*
import android.os.Looper




class MainActivity : AppCompatActivity(), OnStartDragListener, SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        refreshLayout.isRefreshing = true
        resetData()
        mHandler.postDelayed({ refreshLayout.isRefreshing = false }, 1000)
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
        itemHelper.startDrag(viewHolder)
    }


    lateinit var adapter: RecycleViewAdapter
    lateinit var itemHelper: ItemTouchHelper
    private var lastVisibleItem = 0
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var refreshLayout : SwipeRefreshLayout
    private val mHandler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(cn.dengxijian.demo.R.layout.recycleview)
        initView()
    }


    fun initView() {

        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        adapter = RecycleViewAdapter(this, this, initData())
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(TextItemDecoration())
        itemHelper = ItemTouchHelper(MyItemTouchCallBack(adapter))
        itemHelper.attachToRecyclerView(recyclerView)
        refreshLayout =  findViewById(cn.dengxijian.demo.R.id.swipeRefresh)
        refreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_green_light
        )
        refreshLayout.setOnRefreshListener(this)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    // 如果没有隐藏footView，那么最后一个条目的位置就比我们的getItemCount少1，自己可以算一下
                    if (adapter.isFadeTips() === false && lastVisibleItem + 1 === adapter.itemCount) {
                        mHandler.postDelayed({
                            updateList()
                        }, 500)
                    }

                    // 如果隐藏了提示条，我们又上拉加载时，那么最后一个条目就要比getItemCount要少2
                    if (adapter.isFadeTips() === true && lastVisibleItem + 2 === adapter.itemCount) {
                        mHandler.postDelayed({
                            resetData()
                        }, 500)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 在滑动完成后，拿到最后一个可见的item的位置
                lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
            }
        })
    }


    fun initData() : ArrayList<String> {
        var data : ArrayList<String> = ArrayList()
        for (i in 0..10) {
            data.add("item $i")
        }
        return data
    }

    private fun updateList() {
        var mDataList = adapter.getDataList()
        if (mDataList.size < 30) {
            for (i in 1..9) {
                mDataList.add("${mDataList.size + 1}")
            }
            adapter.setHasMore(true)
        } else {
            adapter.setHasMore(false)
        }
        adapter.notifyDataSetChanged()
    }

    private fun resetData() {
        adapter.setInitData(initData())
        adapter.notifyDataSetChanged()
        adapter.setHasMore(true)
        adapter.setFadeTips(false)
    }

}
