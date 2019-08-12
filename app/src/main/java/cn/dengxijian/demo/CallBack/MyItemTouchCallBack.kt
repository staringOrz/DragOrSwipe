package cn.dengxijian.demo.CallBack

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import cn.dengxijian.demo.Adapter.RecycleViewAdapter
import java.util.*


/**
 * @author dengxijian
 * @date 2019-08-12
 */
class MyItemTouchCallBack constructor(adapter: RecycleViewAdapter) : ItemTouchHelper.Callback() {

    var myAdapter = adapter

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        var dragFlag: Int
        var swipeFlag: Int
        var layoutManager: RecyclerView.LayoutManager? = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            dragFlag = ItemTouchHelper.DOWN or ItemTouchHelper.UP or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            swipeFlag = 0
        } else {
            dragFlag = ItemTouchHelper.DOWN or ItemTouchHelper.UP
            swipeFlag = ItemTouchHelper.END
        }
        return makeMovementFlags(dragFlag, swipeFlag)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(myAdapter.getDataList(), i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(myAdapter.getDataList(), i, i - 1)
            }
        }
        recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position : Int = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.END) {
            myAdapter.getDataList().removeAt(position)
        }
        myAdapter.notifyItemRemoved(position)
    }
}