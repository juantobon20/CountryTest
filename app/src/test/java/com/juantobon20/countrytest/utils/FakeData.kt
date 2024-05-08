package com.juantobon20.countrytest.utils

import com.juantobon20.countrytest.data.models.local.CountryEntity
import com.juantobon20.countrytest.data.models.network.CountryResponse
import com.juantobon20.countrytest.data.models.network.NameResponse

val fakeEntityData = listOf(
    CountryEntity(
        code = "mock",
        common = "common",
        official = "mock",
        capital = "mock",
        region = "",
        subregion = "",
        currency = "",
        area = 0f,
        timeZones = "1, 2, 3",
        flag = null,
        coatOfArm = null,
        startOfWeek = "",
        borderingCountries = "1, 2, 3"
    ),
    CountryEntity(
        code = "mock2",
        common = "common",
        official = "official2",
        capital = "mock",
        region = "",
        subregion = "",
        currency = "",
        area = 0f,
        timeZones = "1, 2, 3",
        flag = null,
        coatOfArm = null,
        startOfWeek = "",
        borderingCountries = "1, 2, 3"
    ),
    CountryEntity(
        code = "mock3",
        common = "common",
        official = "official",
        capital = "mock",
        region = "",
        subregion = "",
        currency = "",
        area = 0f,
        timeZones = "1, 2, 3",
        flag = null,
        coatOfArm = null,
        startOfWeek = "",
        borderingCountries = "1, 2, 3"
    ),
)

val fakeApiData = listOf(
    CountryResponse(
        name = NameResponse(
            common = "Common Test123",
            official = "Official Test123"
        ),
        cioc = "123",
        cca2 = "1234",
        cca3 = null,
        currencies = "TEST",
        capital = listOf("Test"),
        region = "Test",
        subregion = null,
        borders = listOf("Test"),
        area = 1234F,
        timezones = listOf("Test"),
        flags = null,
        coatOfArms = null,
        startOfWeek = "Test"
    ),
    CountryResponse(
        name = NameResponse(
            common = "Common Test1234",
            official = "Official Test1234"
        ),
        cioc = "1234",
        cca2 = "12345",
        cca3 = null,
        currencies = "TEST6",
        capital = listOf("Test6"),
        region = "Test",
        subregion = null,
        borders = listOf("Test6"),
        area = 1234F,
        timezones = listOf("Test6"),
        flags = null,
        coatOfArms = null,
        startOfWeek = "Test6"
    )
)