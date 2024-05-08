package com.juantobon20.countrytest.domain.mappers

import com.juantobon20.countrytest.data.models.network.CountryResponse
import com.juantobon20.countrytest.domain.models.CountryData

fun CountryResponse.mapperData(): CountryData = CountryData(
    code = cca3 ?: cioc ?: cca2 ?: "",
    common = name.common,
    official = name.official,
    capital = if (!capital.isNullOrEmpty()) capital.first() else "Sin capital",
    region = region,
    subregion = subregion ?: "Sin región",
    currency = currencies ?: "No tiene moneda definida",
    area = area,
    timeZones = timezones,
    flag = flags,
    coatOfArm = coatOfArms,
    startOfWeek = startOfWeek,
    borderingCountries = borders ?: emptyList()
)