package com.sonu.loginmvvm.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.sonu.loginmvvm.data.repositories.UserRepository
import com.sonu.loginmvvm.util.ApiException
import com.sonu.loginmvvm.util.Coroutines

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null

    var authListener: AuthListener? = null

    fun getuserLoggedIn() = repository.getUser()

    fun onLoginButtonClicked(view: View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            authListener?.onError("All fields are required")
            return
        }
        //Succcess
        Coroutines.main {
            try {
                val loginResponse = repository.userLogin(email!!, password!!)
                loginResponse.user?.let {
                    authListener?.onSuccess(it)
                    return@main
                }
                authListener?.onError(loginResponse.message!!)
            } catch (e: ApiException) {
                authListener?.onError(e.message!!)
            }
        }


    }
}