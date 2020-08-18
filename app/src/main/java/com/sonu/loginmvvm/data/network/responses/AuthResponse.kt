package com.sonu.loginmvvm.data.network.responses

import com.sonu.loginmvvm.data.db.entities.User

data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)