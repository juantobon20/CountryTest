package com.juantobon20.countrytest.domain.models

data class CountryView(
    val code: String,//
    val common: String,//
    val official: String,//
    val capital: String,//
    val region: String,//
    val subRegion: String,//
    val currency: String,//
    val area: Float,//
    val timeZones: List<String>,
    val flag: String,//
    val coatOfArms: String, //
    val startOfWeek: String, //
    val borderingCountries: List<CountryListView>
)