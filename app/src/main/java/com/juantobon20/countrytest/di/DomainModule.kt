package com.juantobon20.countrytest.di

import android.content.Context
import android.net.ConnectivityManager
import com.juantobon20.countrytest.domain.helper.NetworkHelper
import com.juantobon20.countrytest.domain.helper.NetworkHelperImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {


    @Provides
    @Singleton
    fun provideConnectivityManage(
        @ApplicationContext context: Context
    ) : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Provides
    @Singleton
    fun provideNetworkHelper(
        connectivityManager: ConnectivityManager
    ) : NetworkHelper = NetworkHelperImp(connectivityManager = connectivityManager)
}