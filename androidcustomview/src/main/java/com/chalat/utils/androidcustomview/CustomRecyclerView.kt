package com.chalat.utils.androidcustomview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 *
 * Created by Chalat Chansima on 3/8/18.
 *
 */
open class CustomRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    abstract class BottomListener {
        open fun onBottomNow(onBottomNow: Boolean) {}
    }

    abstract class TopListener {
        open fun onTopNow(onTopNow: Boolean) {}
    }

    interface ItemViewedListener {
        fun onViewItem(positionList: List<Int>)
    }

    interface ScrollPercentageListener {
        fun onScrollFinish(percentage: Float)
    }

    private var linearLayoutManager: LinearLayoutManager? = null
    private var topListener: TopListener? = null
    private var bottomListener: BottomListener? = null
    private var onBottomNow = false
    private var onTopNow = false
    private var onTopScrollListener: RecyclerView.OnScrollListener? = null
    private var onBottomScrollListener: RecyclerView.OnScrollListener? = null
    private var scrollPercentageListener: ScrollPercentageListener? = null
    private var lastItemSeenCache = arrayListOf<Int>()

    fun addTopScrollListener(l: TopListener?) {
        if (l != null) {
            checkLayoutManager()
            onTopScrollListener = createTopScrollListener()
            addOnScrollListener(onTopScrollListener)
            topListener = l
        } else {
            removeOnScrollListener(onTopScrollListener)
            onTopScrollListener = null
        }
    }

    fun addBottomScrollListener(l: BottomListener?) {
        if (l != null) {
            checkLayoutManager()
            onBottomScrollListener = createBottomScrollListener()
            addOnScrollListener(onBottomScrollListener)
            bottomListener = l
        } else {
            removeOnScrollListener(onBottomScrollListener)
            bottomListener = null
        }
    }

    fun addItemViewedListener(l: ItemViewedListener) {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                checkLayoutManager()
                linearLayoutManager?.let {
                    val firstItemPosition = it.findFirstVisibleItemPosition()
                    val lastItemPosition = it.findLastCompletelyVisibleItemPosition()
                    val positionList = IntRange(firstItemPosition, lastItemPosition).toList()
                    positionList.filter { it != -1 }
                    if (positionList != lastItemSeenCache && positionList.isNotEmpty()) {
                        lastItemSeenCache = ArrayList(positionList)
                        l.onViewItem(lastItemSeenCache)
                    }
                }
            }
        })
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        scrollPercentageListener?.onScrollFinish(calculatePercentage())
    }

    fun addScrollPercentageListener(l: ScrollPercentageListener) {
        scrollPercentageListener = l
        addOnScrollListener(object : OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == SCROLL_STATE_IDLE) {
                    l.onScrollFinish(calculatePercentage())
                }
            }
        })
    }

    private fun calculatePercentage(): Float {
        val offset = computeVerticalScrollOffset()
        val extent = computeVerticalScrollExtent()
        val range = computeVerticalScrollRange()
        return 100.0f * offset / (range - extent).toFloat()
    }

    private fun createTopScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            checkOnTop()
        }
    }

    private fun createBottomScrollListener() = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            checkOnBottom()
        }
    }

    private fun checkOnTop() {
        val firstVisible = linearLayoutManager!!.findFirstCompletelyVisibleItemPosition()
        if (firstVisible == 0 || firstVisible == -1 && !canScrollToTop()) {
            if (!onTopNow) {
                onTopNow = true
                topListener?.onTopNow(true)
            }
        } else if (onTopNow) {
            onTopNow = false
            topListener?.onTopNow(false)
        }
    }

    private fun checkOnBottom() {
        val lastVisible = linearLayoutManager!!.findLastCompletelyVisibleItemPosition()
        val size = linearLayoutManager!!.itemCount - 1
        if (lastVisible == size || lastVisible == -1 && !canScrollToBottom()) {
            if (!onBottomNow) {
                onBottomNow = true
                bottomListener?.onBottomNow(true)
            }
        } else if (onBottomNow) {
            onBottomNow = false
            bottomListener?.onBottomNow(false)
        }
    }


    private fun checkLayoutManager() {
        if (layoutManager is LinearLayoutManager)
            linearLayoutManager = layoutManager as LinearLayoutManager
        else
            throw Exception("for using this listener, please set LinearLayoutManager")
    }

    private fun canScrollToTop(): Boolean = canScrollVertically(-1)
    private fun canScrollToBottom(): Boolean = canScrollVertically(1)

}