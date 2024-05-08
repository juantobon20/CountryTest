package com.juantobon20.countrytest.views.search

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.NavUtils
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.juantobon20.countrytest.R
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.databinding.ActivitySearchCountryBinding
import com.juantobon20.countrytest.utils.handleState
import com.juantobon20.countrytest.views.adapter.CountryAdapter
import com.juantobon20.countrytest.views.listeners.IOnClickListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchCountryActivity : AppCompatActivity(), SearchView.OnQueryTextListener, IOnClickListener {


    private lateinit var binding: ActivitySearchCountryBinding
    private val viewModel: SearchCountryActivityViewModel by viewModels()

    private val adapter: CountryAdapter = CountryAdapter(listener = this)

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        setTitle("")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rcCountries.adapter = adapter

        handleState(stateFlow = viewModel.stateFlow) { state ->
            adapter.onLoad(state.countries)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchMenuItem: MenuItem = menu.findItem(R.id.action_search)
        searchMenuItem.expandActionView()
        searchView = searchMenuItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.length < 3) {
            return false
        }

        viewModel.search(newText)
        return true
    }

    override fun <T> onClick(t: T) {
        if (t is CountryListView) {
            setResult(RESULT_OK, Intent()
                .putExtra("code", t.code)
                .putExtra("name", t.name)
            )
            finish()
        }
    }
}