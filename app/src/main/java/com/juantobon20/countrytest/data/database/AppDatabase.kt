package com.juantobon20.countrytest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.juantobon20.countrytest.data.daos.CountryDAO
import com.juantobon20.countrytest.data.models.local.CountryEntity

@Database(entities = [CountryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDAO(): CountryDAO
}