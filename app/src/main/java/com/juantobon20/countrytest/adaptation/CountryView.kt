package com.juantobon20.countrytest.adaptation

data class CountryView(
    val code: String,
    val common: String,
    val official: String,
    val capital: String,
    val region: String,
    val subregion: String,
    val currency: String,
    val area: String,
    val timeZones: List<String>,
    val flag: String?,//
    val coatOfArm: String?, //
    val startOfWeek: String, //
    val borderingCountries: List<CountryListView>,
)
