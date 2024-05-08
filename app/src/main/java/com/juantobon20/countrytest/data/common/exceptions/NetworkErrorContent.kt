package com.juantobon20.countrytest.data.common.exceptions

data class NetworkErrorContent(
    val statusCode: String,
    val error: String,
    val message: String?
)