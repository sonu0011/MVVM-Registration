package com.sonu.loginmvvm.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sonu.loginmvvm.data.db.UserDatabase
import com.sonu.loginmvvm.data.db.entities.User
import com.sonu.loginmvvm.data.network.MyApi
import com.sonu.loginmvvm.data.network.SafeApiRequest
import com.sonu.loginmvvm.data.network.responses.AuthResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val database: UserDatabase,
    private val myApi: MyApi
) : SafeApiRequest() {
    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { myApi.userLogin(email, password) }
    }

    suspend fun upsert(user: User) = database.getUserDao().upSert(user)

    fun getUser() = database.getUserDao().getUser()

    suspend fun signUpUser(name: String, email: String, password: String): AuthResponse {
        return apiRequest { myApi.signupUser(name, email, password) }
    }
}