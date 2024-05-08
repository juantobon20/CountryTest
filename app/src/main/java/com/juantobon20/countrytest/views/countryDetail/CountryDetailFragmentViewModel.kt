package com.juantobon20.countrytest.views.countryDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.juantobon20.countrytest.adaptation.CountryView
import com.juantobon20.countrytest.domain.usecases.FetchCountryByCodeUseCase
import com.juantobon20.countrytest.views.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CountryDetailFragmentViewModel @AssistedInject constructor(
    @Assisted val countryCode: String,
    private val fetchCountryByCodeUseCase: FetchCountryByCodeUseCase
) : BaseViewModel<CountryDetailFragmentViewModel.State, Nothing>(State()) {

    init {
        fetchCountryByCode(countryCode)
    }

    private fun fetchCountryByCode(countryCode: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val countryData = fetchCountryByCodeUseCase(countryCode) ?: throw Exception("Country not found")
                    val borderCountries = countryData.borderingCountries.map {
                        fetchCountryByCodeUseCase(it.trim())?.mapperToCountryList()
                    }
                    val countryView = countryData.mapperToCountryView(borderCountries.filterNotNull())
                    update(currentState().copy(countryView = countryView))
                } catch (ex: Exception) {
                    println(ex)
                }
            }
        }
    }

    data class State(
        val countryView: CountryView? = null
    )

    @AssistedFactory
    interface Factory {
        fun create(countryCode: String): CountryDetailFragmentViewModel
    }

    companion object {

        fun providerFactory(
            factory: Factory,
            countryCode: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {

            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return factory.create(countryCode = countryCode) as T
            }
        }
    }
}