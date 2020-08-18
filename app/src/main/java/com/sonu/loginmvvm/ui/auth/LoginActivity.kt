package com.sonu.loginmvvm.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sonu.loginmvvm.R
import com.sonu.loginmvvm.data.db.UserDatabase
import com.sonu.loginmvvm.data.db.entities.User
import com.sonu.loginmvvm.data.network.MyApi
import com.sonu.loginmvvm.data.repositories.UserRepository
import com.sonu.loginmvvm.databinding.ActivityLoginBinding
import com.sonu.loginmvvm.ui.home.HomeActivity
import com.sonu.loginmvvm.util.hide
import com.sonu.loginmvvm.util.show
import com.sonu.loginmvvm.util.snackbar
import com.sonu.loginmvvm.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import java.net.CacheResponse

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val api = MyApi()
        val db = UserDatabase(this)
        val repository = UserRepository(db, api)
        val authViewModelFactory = AuthViewModelFactory(repository)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel =
            ViewModelProvider(this, authViewModelFactory).get(AuthViewModel::class.java);
        binding.viewmodel = viewModel;
        viewModel.authListener = this

        viewModel.getuserLoggedIn().observe(this, Observer {
            if (it != null) {
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

    }


    override fun onStarted() {
        progress_bar.show()
    }

    override fun onError(message: String) {
        progress_bar.hide()
        root_layout.snackbar(message)
    }

    override fun onSuccess(user: User) {
        root_layout.snackbar("${user.name} is Logged In")
    }
}