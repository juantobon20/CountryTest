package com.juantobon20.countrytest.views.countryDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.juantobon20.countrytest.databinding.ItemCountryDetailBinding
import com.juantobon20.countrytest.domain.models.CountryListView
import com.juantobon20.countrytest.views.base.BaseAdapter

class CountryAdapter: BaseAdapter<CountryListView, CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            binding = ItemCountryDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) =
        holder.onBind(items[position])
}