package com.sonu.loginmvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import com.sonu.loginmvvm.util.NetworkException
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkAvailable())
            throw NetworkException("No Internet connection available , Please try after some time")
        return chain.proceed(chain.request())
    }

    private fun isNetworkAvailable(): Boolean {

        val networkManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }

    }
}