package com.juantobon20.countrytest.views.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juantobon20.countrytest.domain.usecases.FetchCountriesBySearchUseCase
import com.juantobon20.countrytest.utils.MainCoroutineRule
import com.juantobon20.countrytest.utils.fakeEntityData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchCountryActivityViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var fetchCountriesBySearchUseCase: FetchCountriesBySearchUseCase
    private lateinit var searchCountryActivityViewModel: SearchCountryActivityViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        searchCountryActivityViewModel = SearchCountryActivityViewModel(
            fetchCountriesBySearchUseCase = fetchCountriesBySearchUseCase,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `given a search when method is called should return a countries list`() = runTest {
        val search = "official"
        val fakeData = fakeEntityData.filter { it.official == search }.map { it.mapperToCountryData() }
        val fakeCountries = fakeData.map { it.mapperToCountryList() }

        coEvery { fetchCountriesBySearchUseCase(search = search) } answers { flowOf(fakeData) }

        searchCountryActivityViewModel.search(search = search)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = SearchCountryActivityViewModel.State(countries = fakeCountries)

        coVerify(exactly = 1) {
            fetchCountriesBySearchUseCase(search = search)
        }
        Assert.assertEquals(state, searchCountryActivityViewModel.stateFlow.value)
    }
}