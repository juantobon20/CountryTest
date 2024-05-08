package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.models.CountryData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import javax.inject.Inject

class SaveCountriesUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {

    suspend operator fun invoke(countries: List<CountryData>) =
        localRepository.insertAll(countries = countries)
}