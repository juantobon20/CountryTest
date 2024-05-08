package com.juantobon20.countrytest.data.services

import com.juantobon20.countrytest.data.models.network.CountryResponse
import retrofit2.http.GET

interface ApiService {

    @GET("all")
    suspend fun fetchAllCountries() : List<CountryResponse>
}