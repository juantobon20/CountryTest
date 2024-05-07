package com.juantobon20.countrytest.views.countryDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.juantobon20.countrytest.databinding.ItemTimeZoneBinding
import com.juantobon20.countrytest.views.base.BaseAdapter

class TimeZoneAdapter : BaseAdapter<String, TimeZoneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeZoneViewHolder =
        TimeZoneViewHolder(
            binding = ItemTimeZoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TimeZoneViewHolder, position: Int) {
        holder.onBind(items[position])
    }
}