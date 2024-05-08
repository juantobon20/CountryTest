package com.juantobon20.countrytest.repositories

import com.juantobon20.countrytest.data.daos.CountryDAO
import com.juantobon20.countrytest.data.models.local.CountryEntity
import com.juantobon20.countrytest.data.repositories.LocalRepositoryImpl
import com.juantobon20.countrytest.domain.mappers.mapperData
import com.juantobon20.countrytest.domain.repositories.LocalRepository
import com.juantobon20.countrytest.utils.fakeApiData
import com.juantobon20.countrytest.utils.fakeEntityData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class LocalRepositoryImplTest {

    @MockK
    private lateinit var countryDAO: CountryDAO
    private lateinit var localRepository: LocalRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        localRepository = LocalRepositoryImpl(countryDAO = countryDAO)
    }

    @Test
    fun `when the method is called it should return a list of countries saved in room`() = runTest {
        coEvery {
            countryDAO.fetchAllCountries()
        }.answers { flow { emit(fakeEntityData) } }

        val response = localRepository.fetchAllCountries().first()
        coVerify(exactly = 1) {
            countryDAO.fetchAllCountries()
        }

        Assert.assertEquals(fakeEntityData, response)
    }

    @Test
    fun `given a search when the method is called it should return a list of countryEntity`() =
        runTest {
            val search = "official"
            val fakeEntities = fakeEntityData.filter { it.official == search }
            coEvery {
                countryDAO.fetchCountriesBySearch(search = search)
            }.answers { flow { emit(fakeEntities) } }

            val response = localRepository.fetchCountriesBySearch(search = search).first()
            coVerify(exactly = 1) {
                countryDAO.fetchCountriesBySearch(search = search)
            }

            Assert.assertEquals(fakeEntities, response)
        }

    @Test
    fun `give a registered code when the method is called it should return a countryEntity`() =
        runTest {
            val code = "mock2"
            val fakeEntity = fakeEntityData.firstOrNull { it.code == code }

            coEvery {
                countryDAO.fetchCountryByCode(code = code)
            }.answers { fakeEntity }

            val response = localRepository.fetchCountryByCode(code = code)
            coVerify(exactly = 1) {
                countryDAO.fetchCountryByCode(code = code)
            }

            Assert.assertEquals(fakeEntity, response)
        }

    @Test
    fun `give an unregistered code when the method is called it should return null`() =
        runTest {
            val code = "mock4"
            val fakeEntity = fakeEntityData.firstOrNull { it.code == code }

            coEvery {
                countryDAO.fetchCountryByCode(code = code)
            }.answers { fakeEntity }

            val response = localRepository.fetchCountryByCode(code = code)
            coVerify(exactly = 1) {
                countryDAO.fetchCountryByCode(code = code)
            }

            Assert.assertEquals(fakeEntity, response)
        }

    @Test
    fun `given a list of countries when the method is called it should store the countries`() =
        runTest {
            val countriesData = fakeApiData.map { it.mapperData() }
            val countriesEntity = countriesData.map { it.mapperToCountryEntity() }
            coEvery {
                countryDAO.insertAll(countriesEntity)
            } answers { }

            localRepository.insertAll(countriesData)
            coVerify(exactly = 1) {
                countryDAO.insertAll(countriesEntity)
            }
        }
}