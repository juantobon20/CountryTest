package com.juantobon20.countrytest.views.countryDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.databinding.FragmentCountryDetailBinding
import com.juantobon20.countrytest.utils.handleState
import com.juantobon20.countrytest.views.countries.CountriesFragmentDirections
import com.juantobon20.countrytest.views.countryDetail.adapter.CountryAdapter
import com.juantobon20.countrytest.views.countryDetail.adapter.TimeZoneAdapter
import com.juantobon20.countrytest.views.listeners.IOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
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

        binding.rvTimeZones.setAdapter(timeZoneAdapter)
        binding.rvBorderingCountries.setAdapter(countryAdapter)

        handleState(stateFlow = viewModel.stateFlow) { state ->
            if (state.isLoading) {
                binding.lblOfficialName.loadSkeleton(length = 10)
                binding.symbolFlagLayout.startSkeleton()
                binding.symbolCoatLayout.startSkeleton()
                binding.hdCapital.startSkeleton()
                binding.hdArea.startSkeleton()
                binding.hdRegion.startSkeleton()
                binding.hdSubRegion.startSkeleton()
                binding.hdCurrency.startSkeleton()
                binding.hdStartOfWeek.startSkeleton()
                binding.rvTimeZones.startSkeleton(R.layout.item_time_zone_skeleton)
                binding.rvBorderingCountries.startSkeleton(R.layout.item_country_detail_skeleton)
            } else {
                binding.lblOfficialName.hideSkeleton()
                binding.symbolFlagLayout.stopSkeleton()
                binding.symbolCoatLayout.stopSkeleton()
                binding.hdCapital.stopSkeleton()
                binding.hdArea.stopSkeleton()
                binding.hdRegion.stopSkeleton()
                binding.hdSubRegion.stopSkeleton()
                binding.hdCurrency.stopSkeleton()
                binding.hdStartOfWeek.stopSkeleton()
                binding.rvTimeZones.stopSkeleton()
                binding.rvBorderingCountries.stopSkeleton()
            }

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