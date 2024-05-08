package com.juantobon20.countrytest.views.countryDetail.adapter

import com.juantobon20.countrytest.databinding.ItemCountryDetailBinding
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.utils.loadImageFromUrl
import com.juantobon20.countrytest.views.base.BaseViewHolder
import com.juantobon20.countrytest.views.listeners.IOnClickListener

class CountryViewHolder(
    private val binding: ItemCountryDetailBinding,
    private val listener: IOnClickListener
) : BaseViewHolder<CountryListView>(binding.root) {

    override fun onBind(t: CountryListView) {
        binding.lblCountryName.text = t.name
        binding.imgFlag.loadImageFromUrl(t.flag)
        binding.root.setOnClickListener {
            listener.onClick(t)
        }
    }
}