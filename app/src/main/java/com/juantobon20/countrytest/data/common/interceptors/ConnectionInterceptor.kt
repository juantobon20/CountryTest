package com.juantobon20.countrytest.data.common.interceptors

import com.google.gson.Gson
import com.juantobon20.countrytest.data.common.exceptions.NetworkErrorContent
import com.juantobon20.countrytest.data.common.exceptions.ServerError
import java.net.HttpURLConnection
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)
        return when (response.code) {
            HttpURLConnection.HTTP_NOT_FOUND,
            HttpURLConnection.HTTP_NOT_ACCEPTABLE,
            HttpURLConnection.HTTP_BAD_REQUEST,
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            HttpURLConnection.HTTP_FORBIDDEN ->
                throw ServerError(getErrorFromResponse(response.body?.string()))
            else -> response
        }
    }

    private fun getErrorFromResponse(responseAsString: String?): String? {
        val errorString = responseAsString ?: return null
        return try {
            Gson().fromJson(errorString, NetworkErrorContent::class.java).error
        } catch (e: Exception) {
            null
        }
    }
}