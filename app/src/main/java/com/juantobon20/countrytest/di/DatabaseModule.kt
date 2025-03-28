package com.juantobon20.countrytest.di

import android.content.Context
import androidx.room.Room
import com.juantobon20.countrytest.data.daos.CountryDAO
import com.juantobon20.countrytest.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "CountriesDb"
    ).build()

    @Provides
    @Singleton
    fun provideCountryDAO(
        appDatabase: AppDatabase
    ) : CountryDAO = appDatabase.countryDAO()
}