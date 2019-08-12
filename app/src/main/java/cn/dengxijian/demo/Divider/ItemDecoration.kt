package cn.dengxijian.demo.Divider

import android.graphics.Rect
import android.support.v7.widget.RecyclerView

/**
 * @author dengxijian
 * @date 2019-08-13
 */
class TextItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, itemPosition: Int, parent: RecyclerView) {
        outRect.set(1, 1, 1, 1)
    }
}