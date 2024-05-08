package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.helper.NetworkHelper
import com.juantobon20.countrytest.domain.mappers.mapperData
import com.juantobon20.countrytest.domain.models.CountryData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.domain.repositories.NetworkRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchAllCountriesUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val localRepository: LocalRepository,
    private val networkHelper: NetworkHelper
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke() : Flow<List<CountryData>> {
        return if (networkHelper.isInternetAvailable()) {
            networkRepository.fetchAllCountries().flatMapLatest {
                flow {
                    val countriesData = it.map { it.mapperData() }
                    emit(countriesData)
                }
            }
        } else {
            localRepository.fetchAllCountries().flatMapLatest {
                flow {
                    val countriesData = it.map { it.mapperToCountryData() }
                    emit(countriesData)
                }
            }
        }
    }
}