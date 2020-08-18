package com.sonu.loginmvvm.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.sonu.loginmvvm.R
import com.sonu.loginmvvm.databinding.ActivityLoginBinding
import com.sonu.loginmvvm.util.hide
import com.sonu.loginmvvm.util.show
import com.sonu.loginmvvm.util.toast
import kotlinx.android.synthetic.main.activity_login.*
import java.net.CacheResponse

class LoginActivity : AppCompatActivity(), AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)
        val viewModel = ViewModelProvider(this).get(AuthViewModel::class.java);
        binding.viewmodel = viewModel;
        viewModel.authListener = this

    }

    override fun onStarted() {
        progress_bar.show()
    }

    override fun onError(message: String) {
        progress_bar.hide()
        toast(message)
    }

    override fun onSuccess(response: LiveData<String>) {
        response.observe(this, Observer {
            progress_bar.hide()
            toast(it)
        })
    }
}