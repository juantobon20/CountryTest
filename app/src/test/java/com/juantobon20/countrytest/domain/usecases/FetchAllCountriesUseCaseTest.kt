package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.helper.NetworkHelper
import com.juantobon20.countrytest.domain.mappers.mapperData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.domain.repositories.NetworkRepository
import com.juantobon20.countrytest.utils.fakeApiData
import com.juantobon20.countrytest.utils.fakeEntityData
import io.mockk.MockKAnnotations
import io.mockk.called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchAllCountriesUseCaseTest {

    @RelaxedMockK
    private lateinit var networkRepository: NetworkRepository

    @RelaxedMockK
    private lateinit var localRepository: LocalRepository

    @RelaxedMockK
    private lateinit var networkHelper: NetworkHelper

    private lateinit var fetchAllCountriesUseCase: FetchAllCountriesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fetchAllCountriesUseCase = FetchAllCountriesUseCase(
            networkRepository = networkRepository,
            localRepository = localRepository,
            networkHelper = networkHelper
        )
    }

    @Test
    fun `when the method is invoked and there is internet it should get data from the api`() =
        runTest {
            every {
                networkHelper.isInternetAvailable()
            } answers { true }

            coEvery {
                networkRepository.fetchAllCountries()
            } answers { flow { emit(fakeApiData) } }

            val response = fetchAllCountriesUseCase.invoke().first()

            verify(exactly = 1) {
                networkHelper.isInternetAvailable()
            }
            coVerify(exactly = 1) {
                networkRepository.fetchAllCountries()
                localRepository.fetchAllCountries().wasNot(called)
            }

            Assert.assertEquals(fakeApiData.map { it.mapperData() }, response)
        }

    @Test
    fun `when the method is invoked and there is not internet it should get data from the local`() =
        runTest {
            every {
                networkHelper.isInternetAvailable()
            } answers { false }

            coEvery {
                localRepository.fetchAllCountries()
            } answers { flow { emit(fakeEntityData) } }

            val response = fetchAllCountriesUseCase.invoke().first()

            verify(exactly = 1) {
                networkHelper.isInternetAvailable()
            }
            coVerify(exactly = 1) {
                localRepository.fetchAllCountries()
                networkRepository.fetchAllCountries().wasNot(called)
            }

            Assert.assertEquals(fakeEntityData.map { it.mapperToCountryData() }, response)
        }
}