package com.juantobon20.countrytest.views.adapter

import com.juantobon20.countrytest.databinding.ItemCountryBinding
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.utils.loadImageFromUrl
import com.juantobon20.countrytest.views.base.BaseViewHolder
import com.juantobon20.countrytest.views.listeners.IOnClickListener

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val listener: IOnClickListener
) : BaseViewHolder<CountryListView>(binding.root) {

    override fun onBind(t: CountryListView) {
        binding.lblCountryName.text = t.name
        binding.lblCapital.text = t.capital
        binding.imgFlag.loadImageFromUrl(t.flag)

        binding.root.setOnClickListener {
            listener.onClick(t)
        }
    }
}