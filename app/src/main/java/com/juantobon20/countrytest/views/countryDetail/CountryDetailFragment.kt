package com.juantobon20.countrytest.views.countryDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.databinding.FragmentCountryDetailBinding
import com.juantobon20.countrytest.domain.models.CountryListView
import com.juantobon20.countrytest.views.countryDetail.adapter.CountryAdapter
import com.juantobon20.countrytest.views.countryDetail.adapter.TimeZoneAdapter

class CountryDetailFragment : Fragment(){

    private val args: CountryDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentCountryDetailBinding
    private val timeZoneAdapter: TimeZoneAdapter = TimeZoneAdapter()
    private val countryAdapter: CountryAdapter = CountryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)

        binding.symbolCoatLayout.image.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.coat
            )
        )

        binding.hdCapital.setDetailText("Bogotá")
        binding.hdArea.setDetailText("1.000.000 mt2")
        binding.hdRegion.setDetailText("Americá")
        binding.hdSubRegion.setDetailText("Americá del Sur")
        binding.hdCurrency.setDetailText("Moneda Colombiana")
        binding.hdStartOfWeek.setDetailText("Lunes")

        binding.rcTimeZones.adapter = timeZoneAdapter

        binding.rcBorderingCountries.adapter = countryAdapter

        val list = listOf(
            "UTC-05:00", "UTC-05:00", "UTC-05:00", "UTC-05:00", "UTC-05:00", "UTC-05:00"
        )
        timeZoneAdapter.onLoad(list)

        val fake = listOf(
            CountryListView(
                code = "COL",
                name = "Colombia",
                capital = "Bogotá"
            ),
            CountryListView(
                code = "USA",
                name = "Estados Unidos",
                capital = "Washington D. C."
            ),
            CountryListView(
                code = "COL",
                name = "Colombia",
                capital = "Bogotá"
            ),
            CountryListView(
                code = "USA",
                name = "Estados Unidos",
                capital = "Washington D. C."
            ),
            CountryListView(
                code = "COL",
                name = "Colombia",
                capital = "Bogotá"
            ),
            CountryListView(
                code = "USA",
                name = "Estados Unidos",
                capital = "Washington D. C."
            ),
            CountryListView(
                code = "COL",
                name = "Colombia",
                capital = "Bogotá"
            ),
            CountryListView(
                code = "USA",
                name = "Estados Unidos",
                capital = "Washington D. C."
            ),
            CountryListView(
                code = "COL",
                name = "Colombia",
                capital = "Bogotá"
            ),
            CountryListView(
                code = "USA",
                name = "Estados Unidos",
                capital = "Washington D. C."
            ), CountryListView(
                code = "COL",
                name = "Colombia",
                capital = "Bogotá"
            ),
            CountryListView(
                code = "USA",
                name = "Estados Unidos",
                capital = "Washington D. C."
            )
        )
        countryAdapter.onLoad(fake)

        println(args.countryCode)
        return binding.root
    }
}