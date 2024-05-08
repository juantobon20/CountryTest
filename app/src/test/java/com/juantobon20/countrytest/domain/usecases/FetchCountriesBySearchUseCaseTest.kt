package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.utils.fakeEntityData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchCountriesBySearchUseCaseTest {

    @RelaxedMockK
    private lateinit var localRepository: LocalRepository
    private lateinit var fetchCountriesBySearchUseCase: FetchCountriesBySearchUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fetchCountriesBySearchUseCase = FetchCountriesBySearchUseCase(localRepository = localRepository)
    }

    @Test
    fun `given a search when the method is called it should return a list of countryEntity`() =
        runTest {
            val search = "official"
            val fakeEntityData = fakeEntityData.filter { it.official == search }

            coEvery {
                localRepository.fetchCountriesBySearch(search = search)
            } answers {
                flow { emit(fakeEntityData) }
            }

            val response = fetchCountriesBySearchUseCase.invoke(search = search).first()
            coVerify(exactly = 1) {
                localRepository.fetchCountriesBySearch(search = search)
            }

            Assert.assertEquals(fakeEntityData.map { it.mapperToCountryData() }, response)

        }
}