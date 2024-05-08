package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.mappers.mapperData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.utils.fakeApiData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class SaveCountriesUseCaseTest {

    @RelaxedMockK
    private lateinit var localRepository: LocalRepository
    private lateinit var saveCountriesUseCase: SaveCountriesUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        saveCountriesUseCase = SaveCountriesUseCase(localRepository = localRepository)
    }

    @Test
    fun `given a list of countries when the method is invoked it should store the countries`() =
        runTest {
            val countries = fakeApiData.map { it.mapperData() }
            coEvery {
                localRepository.insertAll(countries)
            } answers { }

            saveCountriesUseCase.invoke(countries)
            coVerify(exactly = 1) {
                localRepository.insertAll(countries)
            }
        }
}