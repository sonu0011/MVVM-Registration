package com.sonu.loginmvvm.ui.auth

import androidx.lifecycle.LiveData

interface AuthListener {
    fun onStarted()
    fun onError(message: String)
    fun onSuccess(response: LiveData<String>)
}