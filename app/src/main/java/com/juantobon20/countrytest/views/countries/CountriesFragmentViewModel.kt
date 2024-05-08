package com.juantobon20.countrytest.views.countries

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.domain.helper.NetworkHelper
import com.juantobon20.countrytest.domain.models.CountryData
import com.juantobon20.countrytest.domain.usecases.FetchAllCountriesUseCase
import com.juantobon20.countrytest.domain.usecases.SaveCountriesUseCase
import com.juantobon20.countrytest.views.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CountriesFragmentViewModel @Inject constructor(
    private val fetchAllCountriesUseCase: FetchAllCountriesUseCase,
    private val saveCountriesUseCase: SaveCountriesUseCase,
    private val networkHelper: NetworkHelper
) : BaseViewModel<CountriesFragmentViewModel.State, Nothing>(initialState = State()) {

    fun fetchAllCountries() {
        viewModelScope.launch {
            fetchAllCountriesUseCase()
                .flowOn(Dispatchers.IO)
                .onStart {
                    update(
                        currentState().copy(
                            isLoading = true,
                            countries = emptyList()
                        )
                    )
                }
                .catch { ex ->
                    Log.e("Test", "${ex.message}")
                }.collect { countriesData ->
                    if (networkHelper.isInternetAvailable()) {
                        saveAllCountries(countriesData)
                    }

                    val countriesList = countriesData
                        .map { it.mapperToCountryList() }
                        .sortedBy { it.name }

                    update(
                        currentState().copy(
                            isLoading = false,
                            countries = countriesList
                        )
                    )
                }
        }
    }

    private fun saveAllCountries(countriesData: List<CountryData>) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                saveCountriesUseCase(countriesData)
            }
        }
    }

    data class State(
        val isLoading: Boolean = false,
        var countries: List<CountryListView> = emptyList()
    )
}