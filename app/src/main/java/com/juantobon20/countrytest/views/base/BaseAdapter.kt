package com.juantobon20.countrytest.views.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class BaseAdapter<T, VH: ViewHolder> : RecyclerView.Adapter<VH>() {

    var items: List<T> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun onLoad(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}