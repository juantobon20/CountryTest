package com.juantobon20.countrytest.views.countries.adapter

import com.juantobon20.countrytest.databinding.ItemCountryBinding
import com.juantobon20.countrytest.domain.models.CountryListView
import com.juantobon20.countrytest.views.base.BaseViewHolder
import com.juantobon20.countrytest.views.listeners.IOnClickListener

class CountryViewHolder(
    private val binding: ItemCountryBinding,
    private val listener: IOnClickListener
) : BaseViewHolder<CountryListView>(binding.root) {

    override fun onBind(t: CountryListView) {
        binding.lblCountryName.text = t.name
        binding.lblCapital.text = t.capital
        binding.root.setOnClickListener {
            listener.onClick(t.code)
        }
    }
}