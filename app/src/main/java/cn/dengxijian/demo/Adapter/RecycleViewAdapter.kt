package cn.dengxijian.demo.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.dengxijian.demo.Listenner.OnStartDragListener

/**
 * @author dengxijian
 * @date 2019-08-11
 */
class RecycleViewAdapter constructor(context : Context, startDragListener: OnStartDragListener, dataList : ArrayList<String>) : RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder>()  {

    var mContext =  context
    private var mDataList = dataList
    var mStartDragListener = startDragListener

    fun getDataList() : ArrayList<String> = mDataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MyViewHolder{
       val itemView = LayoutInflater.from(mContext).inflate(cn.dengxijian.demo.R.layout.item, parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = mDataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.setText(mDataList.get(position))

        holder.name.setOnLongClickListener(View.OnLongClickListener { v ->
            mStartDragListener.onStartDrag(holder)
            true
        })
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var name : TextView = itemView.findViewById(cn.dengxijian.demo.R.id.textItem)
    }
}