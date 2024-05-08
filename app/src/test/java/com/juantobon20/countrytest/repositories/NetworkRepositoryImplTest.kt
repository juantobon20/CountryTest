package com.juantobon20.countrytest.repositories

import com.juantobon20.countrytest.data.common.exceptions.ServerError
import com.juantobon20.countrytest.data.repositories.NetworkRepositoryImpl
import com.juantobon20.countrytest.data.services.ApiService
import com.juantobon20.countrytest.domain.repositories.NetworkRepository
import com.juantobon20.countrytest.utils.fakeApiData
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
        } returns fakeApiData

        val response = networkRepository.fetchAllCountries().first()

        coVerify(exactly = 1) {
            apiService.fetchAllCountries()
        }

        assertEquals(fakeApiData, response)
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