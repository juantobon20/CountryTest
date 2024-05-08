package com.juantobon20.countrytest.di

import com.juantobon20.countrytest.data.daos.CountryDAO
import com.juantobon20.countrytest.data.repositories.LocalRepositoryImpl
import com.juantobon20.countrytest.data.repositories.NetworkRepositoryImpl
import com.juantobon20.countrytest.data.services.ApiService
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.domain.repositories.NetworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNetworkRepository(
        apiService: ApiService
    ) : NetworkRepository = NetworkRepositoryImpl(apiService = apiService)

    @Provides
    @Singleton
    fun providerLocalRepository(
        countryDAO: CountryDAO
    ) : LocalRepository = LocalRepositoryImpl(countryDAO = countryDAO)
}