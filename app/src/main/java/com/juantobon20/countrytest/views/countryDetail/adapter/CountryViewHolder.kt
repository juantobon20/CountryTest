package com.juantobon20.countrytest.views.countryDetail.adapter

import com.juantobon20.countrytest.databinding.ItemCountryDetailBinding
import com.juantobon20.countrytest.domain.models.CountryListView
import com.juantobon20.countrytest.views.base.BaseViewHolder

class CountryViewHolder(
    private val binding: ItemCountryDetailBinding
) : BaseViewHolder<CountryListView>(binding.root) {

    override fun onBind(t: CountryListView) {
        binding.lblCountryName.text = t.name
    }
}