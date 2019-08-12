package cn.dengxijian.demo.Listenner

import android.support.v7.widget.RecyclerView



/**
 * @author dengxijian
 * @date 2019-08-12
 */
interface OnStartDragListener {

    /**
     * Called when a view is requesting a start of a drag.
     *
     * @param viewHolder The holder of the view to drag.
     */
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)

}