package com.chalat.utils.androidcustomview

import android.content.Context
import android.support.design.widget.CollapsingToolbarLayout
import android.util.AttributeSet

/**
 *
 * Created by Chalat Chansima on 4/18/18.
 *
 */
class CustomCollapsingToolbarLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CollapsingToolbarLayout(context, attrs, defStyleAttr) {

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        val scrimTriggerHeight = (this.measuredHeight * 0.7).toInt()
        scrimVisibleHeightTrigger = scrimTriggerHeight
    }

}