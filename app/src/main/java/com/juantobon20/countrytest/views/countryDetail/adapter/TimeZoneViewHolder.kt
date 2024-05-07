package com.juantobon20.countrytest.views.countryDetail.adapter

import com.juantobon20.countrytest.databinding.ItemTimeZoneBinding
import com.juantobon20.countrytest.views.base.BaseViewHolder

class TimeZoneViewHolder(
    private val binding: ItemTimeZoneBinding
) : BaseViewHolder<String>(binding.root) {

    override fun onBind(t: String) {
        binding.lblCountryName.text = t
    }
}