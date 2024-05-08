package com.juantobon20.countrytest.di

import com.juantobon20.countrytest.data.services.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun provideApiService(
        retrofit: Retrofit
    ) : ApiService = retrofit.create(ApiService::class.java)
}