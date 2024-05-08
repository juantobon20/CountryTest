package com.juantobon20.countrytest.domain.helper

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import io.mockk.MockKAnnotations
import io.mockk.called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NetworkHelperImpTest {

    @MockK
    private lateinit var connectivityManager: ConnectivityManager
    @MockK
    private lateinit var network: Network

    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        networkHelper = NetworkHelperImp(connectivityManager = connectivityManager)
    }

    @Test
    fun `when the method is called it should respond true`() = runTest {
        every {
            connectivityManager.activeNetwork
        } answers { network }

        every {
            connectivityManager.getNetworkCapabilities(network)
        } answers { NetworkCapabilities() }

        every {
            connectivityManager.getNetworkCapabilities(network)?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        } answers { true }

        val response = networkHelper.isInternetAvailable()
        verify(exactly = 1) {
            connectivityManager.activeNetwork
            connectivityManager.getNetworkCapabilities(network)
        }

        Assert.assertEquals(response, true)
    }

    @Test
    fun `when the method is called and the network is null it should respond false`() = runTest {
        every {
            connectivityManager.activeNetwork
        } answers { null }

        val response = networkHelper.isInternetAvailable()

        verify(exactly = 1) {
            connectivityManager.activeNetwork
            connectivityManager.getNetworkCapabilities(network)?.wasNot(called)
        }

        Assert.assertEquals(response, false)
    }
}