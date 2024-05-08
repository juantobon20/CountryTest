package com.juantobon20.countrytest.data.repositories

import com.juantobon20.countrytest.data.daos.CountryDAO
import com.juantobon20.countrytest.data.models.local.CountryEntity
import com.juantobon20.countrytest.domain.models.CountryData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import kotlinx.coroutines.flow.Flow

class LocalRepositoryImpl(private val countryDAO: CountryDAO) : LocalRepository {
    override suspend fun fetchAllCountries(): Flow<List<CountryEntity>> =
        countryDAO.fetchAllCountries()

    override suspend fun fetchCountriesBySearch(search: String): Flow<List<CountryEntity>> =
        countryDAO.fetchCountriesBySearch(search)

    override suspend fun fetchCountryByCode(code: String): CountryEntity? =
        countryDAO.fetchCountryByCode(code = code)

    override suspend fun insertAll(countries: List<CountryData>) =
        countryDAO.insertAll(countries.map { it.mapperToCountryEntity() })
}