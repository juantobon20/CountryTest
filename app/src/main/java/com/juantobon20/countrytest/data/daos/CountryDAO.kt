package com.juantobon20.countrytest.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.juantobon20.countrytest.data.models.local.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDAO {

    @Query("SELECT * FROM CountryEntity")
    fun fetchAllCountries() : Flow<List<CountryEntity>>

    @Query("SELECT * FROM CountryEntity WHERE " +
            "common LIKE '%' || :search || '%' OR " +
            "official LIKE '%' || :search || '%' OR " +
            "capital LIKE '%' || :search || '%' OR " +
            "region LIKE '%' || :search || '%' OR " +
            "subregion LIKE '%' || :search || '%'")
    fun fetchCountriesBySearch(search: String) : Flow<List<CountryEntity>>

    @Query("SELECT * FROM CountryEntity WHERE code = :code LIMIT 1")
    suspend fun fetchCountryByCode(code: String) : CountryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(countriesList: List<CountryEntity>)

}