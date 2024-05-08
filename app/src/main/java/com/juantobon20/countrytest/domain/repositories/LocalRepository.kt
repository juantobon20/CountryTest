package com.juantobon20.countrytest.domain.repositories

import com.juantobon20.countrytest.data.models.local.CountryEntity
import com.juantobon20.countrytest.domain.models.CountryData
import kotlinx.coroutines.flow.Flow

interface LocalRepository {

    suspend fun fetchAllCountries() : Flow<List<CountryEntity>>

    suspend fun fetchCountriesBySearch(search: String) : Flow<List<CountryEntity>>

    suspend fun fetchCountryByCode(code: String) : CountryEntity?

    suspend fun insertAll(countries: List<CountryData>)
}