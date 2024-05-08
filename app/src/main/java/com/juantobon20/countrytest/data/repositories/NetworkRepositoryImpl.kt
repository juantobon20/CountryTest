package com.juantobon20.countrytest.data.repositories

import com.juantobon20.countrytest.data.models.network.CountryResponse
import com.juantobon20.countrytest.data.services.ApiService
import com.juantobon20.countrytest.domain.repositories.NetworkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NetworkRepositoryImpl(
    private val apiService: ApiService
) : NetworkRepository {

    override suspend fun fetchAllCountries(): Flow<List<CountryResponse>> {
        return flow {
            val countriesResponse = apiService.fetchAllCountries()
            emit(countriesResponse)
        }
    }
}