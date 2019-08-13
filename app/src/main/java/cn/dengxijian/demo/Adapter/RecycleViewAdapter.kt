package cn.dengxijian.demo.Adapter

import android.content.Context
import android.os.Handler
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.dengxijian.demo.Listenner.OnStartDragListener
import android.support.v4.os.HandlerCompat.postDelayed
import android.os.Looper


/**
 * @author dengxijian
 * @date 2019-08-11
 */
class RecycleViewAdapter constructor(
    context: Context,
    startDragListener: OnStartDragListener,
    dataList: ArrayList<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext = context
    private var mDataList = dataList
    private val normalType = 0     // 第一种ViewType，正常的item
    private val footType = 1       // 第二种ViewType，底部的提示View
    private var fadeTips = false  // 是否隐藏foot
    private var hasMore = true   // 变量，是否有更多数据
    private var mStartDragListener = startDragListener
    private val mHandler = Handler(Looper.getMainLooper())

    fun getDataList(): ArrayList<String> = mDataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == normalType) {
            val itemView = LayoutInflater.from(mContext).inflate(cn.dengxijian.demo.R.layout.item, parent, false)
            return MyItemViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(mContext).inflate(cn.dengxijian.demo.R.layout.footview, parent, false)
            return MyfootViewHolder(itemView)
        }
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(holderItem: RecyclerView.ViewHolder, position: Int) {

        if (holderItem is MyItemViewHolder) {
            holderItem.name.setText(mDataList.get(position))

            holderItem.name.setOnLongClickListener(View.OnLongClickListener { v ->
                mStartDragListener.onStartDrag(holderItem)
                true
            })
        } else {
            if(!fadeTips) {
                mHandler.post(Runnable {
                    // 隐藏提示条
                    (holderItem as MyfootViewHolder).name.setVisibility(View.VISIBLE)
                })
            }
            if (hasMore) {
                (holderItem as MyfootViewHolder).name.setText("正在加载更多...")
            } else {
                (holderItem as MyfootViewHolder).name.setText("没有更多数据了...")
                //(holderItem as MyfootViewHolder).name.setVisibility(View.VISIBLE)
                // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                mHandler.postDelayed(Runnable {
                    // 隐藏提示条
                    (holderItem as MyfootViewHolder).name.setVisibility(View.GONE)
                    fadeTips = true
                }, 500)

            }
        }
    }

    // 暴露接口，改变fadeTips的方法
    fun isFadeTips(): Boolean {
        return fadeTips
    }

    fun setFadeTips(fade: Boolean) {
        fadeTips =fade
    }
    override fun getItemViewType(position: Int): Int {
        if (position == mDataList.size - 1) {
            return footType
        } else {
            return normalType
        }
    }

    fun setHasMore(boolean: Boolean) {
        hasMore = boolean
    }
    fun setInitData(list :ArrayList<String>){
        mDataList = list
    }



    inner class MyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(cn.dengxijian.demo.R.id.textItem)
    }

    inner class MyfootViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(cn.dengxijian.demo.R.id.tips)
    }
}