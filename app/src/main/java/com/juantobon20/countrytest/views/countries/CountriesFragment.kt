package com.juantobon20.countrytest.views.countries

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.databinding.FragmentCountriesBinding
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.utils.handleState
import com.juantobon20.countrytest.views.adapter.CountryAdapter
import com.juantobon20.countrytest.views.listeners.IOnClickListener
import com.juantobon20.countrytest.views.search.SearchCountryActivity
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class CountriesFragment : Fragment(), IOnClickListener {

    private val searchResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        result.data?.extras?.let {
            val code = it.getString("code", "")
            val name = it.getString("name", "")

            navigateToDetail(code, name)
        }
    }

    private lateinit var binding: FragmentCountriesBinding
    private val viewModel: CountriesFragmentViewModel by viewModels()

    private val adapter: CountryAdapter = CountryAdapter(listener = this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rcCountries.adapter = adapter

        requireActivity().addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_countries_fragment, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.action_search) {
                    searchResult.launch(Intent(requireContext(), SearchCountryActivity::class.java))
                    return true
                }
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewModel.fetchAllCountries()
        handles()
    }

    private fun handles() {
        handleState(stateFlow = viewModel.stateFlow) { state ->
            if (state.isLoading) {
                binding.rcCountries.loadSkeleton(R.layout.item_country_skeleton) {
                    itemCount(10)
                }
            } else {
                binding.rcCountries.hideSkeleton()
            }

            adapter.onLoad(state.countries)
        }
    }

    private fun navigateToDetail(code: String, name: String) {
        val action = CountriesFragmentDirections.actionCountriesFragmentToCountryDetailFragment(
            countryCode = code,
            countryName = name
        )
        findNavController().navigate(action)
    }

    override fun <T> onClick(t: T) {
        if (t is CountryListView) {
            navigateToDetail(t.code, t.name)
        }
    }
}