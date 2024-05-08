package com.juantobon20.countrytest.views.search

import androidx.lifecycle.viewModelScope
import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.domain.usecases.FetchCountriesBySearchUseCase
import com.juantobon20.countrytest.views.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCountryActivityViewModel @Inject constructor(
    private val fetchCountriesBySearchUseCase: FetchCountriesBySearchUseCase
) : BaseViewModel<SearchCountryActivityViewModel.State, Nothing>(State()) {


    fun search(search: String) {
        viewModelScope.launch {
            if (search.trim().isEmpty()) {
                update(currentState().copy(countries = emptyList()))
                return@launch
            }

            fetchCountriesBySearchUseCase(search)
                .flowOn(Dispatchers.IO)
                .catch { ex -> print(ex) }
                .collect { countries ->
                    val countriesListView = countries.map { it.mapperToCountryList() }
                    update(
                        currentState().copy(
                            countries = countriesListView
                        )
                    )
                }
        }
    }

    data class State(
        val countries: List<CountryListView> = emptyList()
    )
}