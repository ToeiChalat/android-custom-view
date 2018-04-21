package com.chalat.utils.androidcustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView

/**
 *
 * Created by Chalat Chansima on 2/6/18.
 *
 */
class CustomDividerItemDecorator private constructor(context: Context?, orientation: Int)
    : DividerItemDecoration(context, orientation) {

    private var marginLeft = 0
    private var marginRight = 0

    constructor(context: Context?, orientation: Int, margin:Int) : this(context, orientation) {
        this.marginLeft = margin
        this.marginRight = margin
    }

    constructor(context: Context?, orientation: Int, marginLeft: Int, marginRight: Int)
            : this(context, orientation) {
        this.marginLeft = marginLeft
        this.marginRight = marginRight
    }


    private val attributes = intArrayOf(android.R.attr.listDivider)
    private val divider : Drawable?

    init {
        val styledAttributes = context?.obtainStyledAttributes(attributes)
        divider = styledAttributes?.getDrawable(0)
        styledAttributes?.recycle()
    }


    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft + marginLeft
        val right = parent.width - parent.paddingRight - marginRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val intrinsicHeight = divider?.intrinsicHeight ?: 0
            val top = child.bottom + params.bottomMargin
            val bottom = top + intrinsicHeight

            divider?.setBounds(left, top, right, bottom)
            divider?.draw(c)
        }
    }

}