package com.juantobon20.countrytest.domain.repositories

import com.juantobon20.countrytest.data.models.network.CountryResponse
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {

    suspend fun fetchAllCountries() : Flow<List<CountryResponse>>
}