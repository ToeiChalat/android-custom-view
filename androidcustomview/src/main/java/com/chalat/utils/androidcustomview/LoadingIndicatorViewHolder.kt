package com.chalat.utils.androidcustomview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
 * Created by Chalat Chansima on 2/19/18.
 *
 */
class LoadingIndicatorViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun getViewHolder(layoutId: Int, parent: ViewGroup): LoadingIndicatorViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                    .inflate(layoutId, parent, false)
            return LoadingIndicatorViewHolder(itemView)
        }
    }

    fun setLoadFinish(loadFinish: Boolean) {
        if (loadFinish) View.GONE else View.VISIBLE
    }

}
