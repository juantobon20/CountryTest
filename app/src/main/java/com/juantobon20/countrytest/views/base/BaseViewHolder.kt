package com.juantobon20.countrytest.views.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T>(view: View) : ViewHolder(view) {

    abstract fun onBind(t: T)
}