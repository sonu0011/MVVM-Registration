package com.sonu.loginmvvm.util

import java.io.IOException

class ApiExceptions(message: String) : IOException(message)
class NetworkException(message: String) : IOException(message)
