package com.sonu.loginmvvm.ui.auth

import com.sonu.loginmvvm.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onError(message: String)
    fun onSuccess(user: User)
}