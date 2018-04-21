package com.chalat.utils.androidcustomview

import android.content.Context
import android.util.AttributeSet

/**
 *
 * Created by Chalat Chansima on 3/8/18.
 *
 */
class LoadMoreRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CustomRecyclerView(context, attrs, defStyleAttr) {

    interface LoadMoreListener {
        fun onLoadMore()
    }

    fun addLoadMoreListener(l: LoadMoreListener) {
        addBottomScrollListener(object: BottomListener() {
            override fun onBottomNow(onBottomNow: Boolean) {
                if (onBottomNow) {
                    l.onLoadMore()
                }
            }
        })
    }

}