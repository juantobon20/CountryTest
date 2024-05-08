package com.juantobon20.countrytest.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juantobon20.countrytest.domain.models.CountryData

@Entity
data class CountryEntity(
    @PrimaryKey val code: String,
    val common: String,
    val official: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val currency: String,
    val area: Float,
    val timeZones: String,
    val flag: String?,//
    val coatOfArm: String?, //
    val startOfWeek: String, //
    val borderingCountries: String?,
) {

    fun mapperToCountryData(): CountryData =
        CountryData(
            code = code,
            common = common,
            official = official,
            capital = capital,
            region = region,
            subregion = subregion,
            currency = currency,
            area = area,
            timeZones = timeZones.split(",").toList(),
            flag = flag,
            coatOfArm = coatOfArm,
            startOfWeek = startOfWeek,
            borderingCountries = borderingCountries?.split(",")?.toList() ?: emptyList()
        )
}