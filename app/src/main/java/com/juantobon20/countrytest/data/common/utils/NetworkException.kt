package com.juantobon20.countrytest.data.common.utils

import java.io.IOException

open class ApiException(message: String?) : IOException(message)
class NoInternet(message: String?) : ApiException(message)
class ServerError(message: String?) : ApiException(message)
