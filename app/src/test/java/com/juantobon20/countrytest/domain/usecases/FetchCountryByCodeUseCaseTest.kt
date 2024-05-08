package com.juantobon20.countrytest.domain.usecases

import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.utils.fakeEntityData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class FetchCountryByCodeUseCaseTest {

    @RelaxedMockK
    private lateinit var localRepository: LocalRepository
    private lateinit var fetchCountryByCodeUseCase: FetchCountryByCodeUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        fetchCountryByCodeUseCase = FetchCountryByCodeUseCase(localRepository = localRepository)
    }

    @Test
    fun `give a registered code when the method is called it should return a countryEntity`() =
        runTest {
            val code = "mock2"
            val fakeEntity = fakeEntityData.firstOrNull { it.code == code }

            coEvery {
                localRepository.fetchCountryByCode(code = code)
            }.answers { fakeEntity }

            val response = fetchCountryByCodeUseCase.invoke(code = code)
            coVerify(exactly = 1) {
                localRepository.fetchCountryByCode(code = code)
            }

            Assert.assertEquals(fakeEntity?.mapperToCountryData(), response)
        }

    @Test
    fun `give an unregistered code when the method is called it should return null`() =
        runTest {
            val code = "mock4"
            val fakeEntity = fakeEntityData.firstOrNull { it.code == code }

            coEvery {
                localRepository.fetchCountryByCode(code = code)
            }.answers { fakeEntity }

            val response = fetchCountryByCodeUseCase.invoke(code = code)
            coVerify(exactly = 1) {
                localRepository.fetchCountryByCode(code = code)
            }

            Assert.assertEquals(fakeEntity, response)
        }
}