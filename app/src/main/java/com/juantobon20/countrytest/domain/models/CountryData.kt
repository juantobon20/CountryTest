package com.juantobon20.countrytest.domain.models

import com.juantobon20.countrytest.adaptation.CountryListView
import com.juantobon20.countrytest.adaptation.CountryView
import com.juantobon20.countrytest.data.models.local.CountryEntity

data class CountryData(
    val code: String,
    val common: String,
    val official: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val currency: String,
    val area: Float,
    val timeZones: List<String>,
    val flag: String?,
    val coatOfArm: String?,
    val startOfWeek: String,
    val borderingCountries: List<String>,
) {

    fun mapperToCountryList(): CountryListView =
        CountryListView(
            code = code,
            name = common,
            capital = capital,
            flag = flag
        )

    fun mapperToCountryView(borderingCountries: List<CountryListView>): CountryView =
        CountryView(
            code = code,
            common = common,
            official = official,
            capital = capital,
            region = region,
            subregion = subregion,
            currency = currency,
            area = String.format("%,8.2f mt2", area),
            timeZones = timeZones,
            flag = flag,
            coatOfArm = coatOfArm,
            startOfWeek = startOfWeek,
            borderingCountries = borderingCountries
        )

    fun mapperToCountryEntity(): CountryEntity =
        CountryEntity(
            code = code,
            common = common,
            official = official,
            capital = capital,
            region = region,
            subregion = subregion,
            currency = currency,
            area = area,
            timeZones = timeZones.joinToString(),
            flag = flag,
            coatOfArm = coatOfArm,
            startOfWeek = startOfWeek,
            borderingCountries = borderingCountries.joinToString()
        )
}