package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.models.CountryData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCountriesBySearchUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(search: String) : Flow<List<CountryData>> =
        localRepository.fetchCountriesBySearch(search = search).flatMapLatest {
            flow {
                val countriesData = it.map { it.mapperToCountryData() }
                emit(countriesData)
            }
        }
}