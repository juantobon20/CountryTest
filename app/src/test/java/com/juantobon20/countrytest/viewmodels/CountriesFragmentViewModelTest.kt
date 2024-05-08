package com.juantobon20.countrytest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juantobon20.countrytest.domain.helper.NetworkHelper
import com.juantobon20.countrytest.domain.mappers.mapperData
import com.juantobon20.countrytest.domain.usecases.FetchAllCountriesUseCase
import com.juantobon20.countrytest.domain.usecases.SaveCountriesUseCase
import com.juantobon20.countrytest.utils.MainCoroutineRule
import com.juantobon20.countrytest.utils.fakeApiData
import com.juantobon20.countrytest.views.countries.CountriesFragmentViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountriesFragmentViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var fetchAllCountriesUseCase: FetchAllCountriesUseCase
    @MockK
    private lateinit var saveCountriesUseCase: SaveCountriesUseCase
    @MockK
    private lateinit var networkHelper: NetworkHelper

    private lateinit var countriesFragmentViewModel: CountriesFragmentViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        countriesFragmentViewModel = CountriesFragmentViewModel(
            fetchAllCountriesUseCase = fetchAllCountriesUseCase,
            saveCountriesUseCase = saveCountriesUseCase,
            networkHelper = networkHelper,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `when the method is called should update state with isLoading in true`() {
        coEvery { fetchAllCountriesUseCase() } returns emptyFlow()

        countriesFragmentViewModel.fetchAllCountries()
        testDispatcher.scheduler.advanceUntilIdle()

        val currentState = CountriesFragmentViewModel.State(isLoading = true)
        Assert.assertEquals(currentState, countriesFragmentViewModel.stateFlow.value)
    }

    @Test
    fun `given internet connection when the method is called should update state with the countries and save data`() = runTest {
        val fakeApiData = fakeApiData.map { it.mapperData() }

        every { networkHelper.isInternetAvailable() } answers { true }

        coEvery { fetchAllCountriesUseCase() } answers { flowOf(fakeApiData) }

        coEvery { saveCountriesUseCase(fakeApiData) } answers { }

        countriesFragmentViewModel.fetchAllCountries()
        testDispatcher.scheduler.advanceUntilIdle()

        val expectedCountries = fakeApiData.map { it.mapperToCountryList() }.sortedBy { it.name }
        val state = CountriesFragmentViewModel.State(countries = expectedCountries)

        coVerify(exactly = 1) {
            networkHelper.isInternetAvailable()
            saveCountriesUseCase(fakeApiData)
        }
        Assert.assertEquals(state, countriesFragmentViewModel.stateFlow.value)
    }

    @Test
    fun `given not internet connection when the method is called should update state with the countries`() = runTest {
        val fakeApiData = fakeApiData.map { it.mapperData() }

        every { networkHelper.isInternetAvailable() } answers { false }

        coEvery { fetchAllCountriesUseCase() } answers { flowOf(fakeApiData) }
        coEvery { saveCountriesUseCase(fakeApiData) } answers { }

        countriesFragmentViewModel.fetchAllCountries()
        testDispatcher.scheduler.advanceUntilIdle()

        val expectedCountries = fakeApiData.map { it.mapperToCountryList() }.sortedBy { it.name }
        val state = CountriesFragmentViewModel.State(countries = expectedCountries)

        verify(exactly = 1) {
            networkHelper.isInternetAvailable()
        }
        coVerify(exactly = 0) {
            saveCountriesUseCase(fakeApiData)
        }

        Assert.assertEquals(state, countriesFragmentViewModel.stateFlow.value)
    }
}