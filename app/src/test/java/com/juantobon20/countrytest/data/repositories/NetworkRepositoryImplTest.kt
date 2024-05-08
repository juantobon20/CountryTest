package com.juantobon20.countrytest.data.repositories

import com.juantobon20.countrytest.data.common.exceptions.ServerError
import com.juantobon20.countrytest.data.models.network.CountryResponse
import com.juantobon20.countrytest.data.models.network.NameResponse
import com.juantobon20.countrytest.data.services.ApiService
import com.juantobon20.countrytest.domain.repositories.NetworkRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NetworkRepositoryImplTest {

    @RelaxedMockK
    private lateinit var apiService: ApiService
    private lateinit var networkRepository: NetworkRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        networkRepository = NetworkRepositoryImpl(apiService)
    }

    @Test
    fun `when the method is called it should return a list of countries`() = runTest {
        coEvery {
            apiService.fetchAllCountries()
        } returns fakeData

        val response = networkRepository.fetchAllCountries().first()

        coVerify(exactly = 1) {
            apiService.fetchAllCountries()
        }

        assertEquals(fakeData, response)
    }

    @Test
    fun `when the method is called and there is a ServerError, it should be returned`() = runTest {
        val exception = ServerError("Mock Error")
        coEvery {
            apiService.fetchAllCountries()
        }.throws(exception)

        try {
            networkRepository.fetchAllCountries().first()
        } catch (ex: Exception) {
            coVerify(exactly = 1) {
                apiService.fetchAllCountries()
            }
            assertEquals(exception, ex)
        }
    }
}

val fakeData = listOf(
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