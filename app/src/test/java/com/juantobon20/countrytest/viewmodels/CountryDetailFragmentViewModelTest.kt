package com.juantobon20.countrytest.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.juantobon20.countrytest.domain.usecases.FetchCountryByCodeUseCase
import com.juantobon20.countrytest.utils.MainCoroutineRule
import com.juantobon20.countrytest.utils.fakeEntityData
import com.juantobon20.countrytest.views.countryDetail.CountryDetailFragmentViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CountryDetailFragmentViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK
    private lateinit var fetchCountryByCodeUseCase: FetchCountryByCodeUseCase
    private lateinit var countryDetailFragmentViewModel: CountryDetailFragmentViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        countryDetailFragmentViewModel = CountryDetailFragmentViewModel(
            countryCode = "mock",
            fetchCountryByCodeUseCase = fetchCountryByCodeUseCase,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `fetchCountryByCode updates stateFlow with country and border countries on success`() = runTest {
        val countryData = fakeEntityData.first { it.code == "mock" }.mapperToCountryData()
        val countryView = countryData.mapperToCountryView(
            listOf(
                countryData.mapperToCountryList(),
                countryData.mapperToCountryList(),
                countryData.mapperToCountryList()
            )
        )

        coEvery { fetchCountryByCodeUseCase(code = "mock") } answers { countryData }

        coEvery { fetchCountryByCodeUseCase(code = "1") } answers { countryData }
        coEvery { fetchCountryByCodeUseCase(code = "2") } answers { countryData }
        coEvery { fetchCountryByCodeUseCase(code = "3") } answers { countryData }

        countryDetailFragmentViewModel.fetchCountryByCode()
        testDispatcher.scheduler.advanceUntilIdle()

        val state = CountryDetailFragmentViewModel.State(countryView = countryView)

        coVerify(exactly = 1) {
            fetchCountryByCodeUseCase(code = "mock")
            fetchCountryByCodeUseCase(code = "1")
            fetchCountryByCodeUseCase(code = "2")
            fetchCountryByCodeUseCase(code = "3")
        }
        Assert.assertEquals(state, countryDetailFragmentViewModel.stateFlow.value)
    }
}