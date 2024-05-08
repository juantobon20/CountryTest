package com.juantobon20.countrytest.data.models.network

import com.google.gson.annotations.JsonAdapter
import com.juantobon20.countrytest.data.common.converters.CurrencyNameDeserialize
import com.juantobon20.countrytest.data.common.converters.ImageUrlDeserialize

data class CountryResponse(
    val name: NameResponse,
    val cioc: String?,
    val cca2: String?,
    val cca3: String?,
    @JsonAdapter(CurrencyNameDeserialize::class) val currencies: String?,
    val capital: List<String>?,
    val region: String,
    val subregion: String?,
    val borders: List<String>?,
    val area: Float,
    val timezones: List<String>,
    @JsonAdapter(ImageUrlDeserialize::class) val flags: String?,
    @JsonAdapter(ImageUrlDeserialize::class) val coatOfArms: String?,
    val startOfWeek: String
)

data class NameResponse(
    val common: String,
    val official: String
)

data class CurrencyResponse(
    val name: String,
    val symbol: String
)

data class ImageResponse(
    val png: String?,
    val svg: String?
)