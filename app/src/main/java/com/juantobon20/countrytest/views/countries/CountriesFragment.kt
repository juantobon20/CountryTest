package com.juantobon20.countrytest.views.countries

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.databinding.FragmentCountriesBinding
import com.juantobon20.countrytest.domain.models.CountryListView
import com.juantobon20.countrytest.views.countries.adapter.CountryAdapter
import com.juantobon20.countrytest.views.listeners.IOnClickListener

class CountriesFragment : Fragment(), IOnClickListener {

    private lateinit var binding: FragmentCountriesBinding
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

        binding.rcWorkLoad.adapter = adapter

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
        adapter.onLoad(fake)

        requireActivity().addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_countries_fragment, menu)
                val searchView = menu.findItem(R.id.action_search).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        println(query)
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        println(newText)
                        return false
                    }

                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = false

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun <T> onClick(t: T) {
        val action = CountriesFragmentDirections.actionCountriesFragmentToCountryDetailFragment("COL", "Colombia")
        findNavController().navigate(action)
    }
}