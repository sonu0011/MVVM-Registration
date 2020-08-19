package com.sonu.loginmvvm.ui.auth

import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModel
import com.sonu.loginmvvm.data.repositories.UserRepository
import com.sonu.loginmvvm.util.ApiExceptions
import com.sonu.loginmvvm.util.Coroutines
import com.sonu.loginmvvm.util.NetworkException

class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var name: String? = null
    var passwordConfrim: String? = null

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
                    repository.upsert(it)
                    return@main
                }
                authListener?.onError(loginResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onError(e.message!!)
            } catch (e: NetworkException) {
                authListener?.onError(e.message!!)

            }
        }


    }

    fun onSignupButtonclicked(view: View) {
        authListener?.onStarted()

        if (name.isNullOrEmpty() || password.isNullOrEmpty() || passwordConfrim.isNullOrEmpty()) {
            authListener?.onError("All fields are required")
            return
        }
        Coroutines.main {
            try {
                val signupResponse = repository.signUpUser(name!!, email!!, password!!)
                signupResponse.user?.let {
                    authListener?.onSuccess(it)
                    repository.upsert(it)
                    return@main
                }
                authListener?.onError(signupResponse.message!!)
            } catch (e: ApiExceptions) {
                authListener?.onError(e.message!!)
            } catch (e: NetworkException) {
                authListener?.onError(e.message!!)

            }
        }

    }

    fun gotoSignUpActivity(view: View) {
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun gotoLoginUpActivity(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
}