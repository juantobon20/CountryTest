package com.juantobon20.countrytest.data.common.exceptions

import java.io.IOException

class ServerError(message: String?) : IOException(message)
class TimeOut(message: String?) : IOException(message)
