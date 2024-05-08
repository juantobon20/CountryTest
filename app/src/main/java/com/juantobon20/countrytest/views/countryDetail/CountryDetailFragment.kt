package com.juantobon20.countrytest.views.countryDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.databinding.FragmentCountryDetailBinding
import com.juantobon20.countrytest.utils.handleState
import com.juantobon20.countrytest.views.countries.CountriesFragmentDirections
import com.juantobon20.countrytest.views.countryDetail.adapter.CountryAdapter
import com.juantobon20.countrytest.views.countryDetail.adapter.TimeZoneAdapter
import com.juantobon20.countrytest.views.listeners.IOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CountryDetailFragment : Fragment(), IOnClickListener {

    private val timeZoneAdapter: TimeZoneAdapter = TimeZoneAdapter()
    private val countryAdapter: CountryAdapter = CountryAdapter(listener = this)

    private val args: CountryDetailFragmentArgs by navArgs()
    private lateinit var binding: FragmentCountryDetailBinding

    @Inject
    lateinit var factory: CountryDetailFragmentViewModel.Factory
    private val viewModel: CountryDetailFragmentViewModel by viewModels {
        CountryDetailFragmentViewModel.providerFactory(
            factory = factory,
            countryCode = args.countryCode
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcTimeZones.adapter = timeZoneAdapter
        binding.rcBorderingCountries.adapter = countryAdapter

        handleState(stateFlow = viewModel.stateFlow) { state ->
            state.countryView?.let { countryView ->

                binding.symbolFlagLayout.getImageFromUrl(countryView.flag)
                binding.symbolCoatLayout.getImageFromUrl(countryView.coatOfArm)

                binding.lblOfficialName.text = countryView.official
                binding.hdCapital.setDetailText(countryView.capital)
                binding.hdArea.setDetailText(countryView.area)
                binding.hdRegion.setDetailText(countryView.region)
                binding.hdSubRegion.setDetailText(countryView.subregion)
                binding.hdCurrency.setDetailText(countryView.currency)
                binding.hdStartOfWeek.setDetailText(countryView.startOfWeek)
                timeZoneAdapter.onLoad(countryView.timeZones)
                countryAdapter.onLoad(countryView.borderingCountries)
            }
        }
    }

    override fun <T> onClick(t: T) {
        if (t is CountryListView) {
            val action = CountryDetailFragmentDirections.actionCountryDetailFragmentSelf(
                countryCode = t.code,
                countryName = t.name
            )
            findNavController().navigate(action)
        }
    }
}