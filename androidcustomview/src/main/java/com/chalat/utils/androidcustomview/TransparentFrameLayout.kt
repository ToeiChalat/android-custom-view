package com.chalat.utils.androidcustomview

import android.content.Context
import android.widget.FrameLayout
import android.graphics.*
import android.support.v4.content.ContextCompat.getColor
import android.util.AttributeSet
import android.graphics.RectF
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.util.DisplayMetrics


/**
 *
 * Created by Chalat Chansima on 11/16/17.
 *
 */
class TransparentFrameLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val semitransparentPaint = Paint()
    private val squareBorderPaint = Paint()
    private lateinit var rect: RectF
    private lateinit var dark: Array<RectF>

    private var squareSize = 296

    init {
        init(context)
    }

    private fun init(context: Context) {
        setWillNotDraw(false)
        val screenWidth = context.resources.displayMetrics.widthPixels.toFloat()
        val screenHeight = context.resources.displayMetrics.heightPixels.toFloat()
        squareSize = dpToPx(context, squareSize)
        val x1: Float = (screenWidth - squareSize) / 2
        val x2: Float
        val y1: Float = (screenHeight - squareSize) / 2
        val y2: Float
        x2 = x1 + squareSize
        y2 = y1 + squareSize
        rect = RectF(x1, y1, x2, y2)
        dark = arrayOf(RectF(0f, 0f, screenWidth, y1),
                RectF(0f, y1, x1, y2), RectF(x2, y1, screenWidth, y2),
                RectF(0f, y2, screenWidth, screenHeight))
    }

    private fun setSurroundColor(@ColorRes color: Int) {
        semitransparentPaint.color = getColor(context, color)
    }

    fun setStroke(@ColorRes color: Int, @DimenRes width: Int) {
        squareBorderPaint.style = Paint.Style.STROKE
        squareBorderPaint.color = getColor(context, color)
        squareBorderPaint.strokeWidth = resources
                .getDimensionPixelSize(width).toFloat()
    }

    private fun dpToPx(context: Context, squareSize: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        return Math.round(squareSize * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    override fun onDraw(canvas: Canvas) {
        for (r in dark) {
            canvas.drawRect(r, semitransparentPaint)
        }
        canvas.drawRoundRect(rect, 30F, 30F, squareBorderPaint)
    }

}