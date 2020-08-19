package com.sonu.loginmvvm.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sonu.loginmvvm.R
import com.sonu.loginmvvm.data.db.entities.User
import com.sonu.loginmvvm.databinding.ActivitySignupBinding
import com.sonu.loginmvvm.ui.home.HomeActivity
import com.sonu.loginmvvm.util.hide
import com.sonu.loginmvvm.util.show
import com.sonu.loginmvvm.util.snackbar
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.progress_bar
import kotlinx.android.synthetic.main.activity_signup.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware, AuthListener {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySignupBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_signup)
        val viewModel =
            ViewModelProvider(this, factory).get(AuthViewModel::class.java);
        binding.viewmodel = viewModel;
        viewModel.authListener = this

        viewModel.getuserLoggedIn().observe(this, Observer {
            if (it != null) {
                signup_root_layout.snackbar("${it.name} is logged in")
                Intent(this, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

    }

    override fun onStarted() {
        progress_bar_signup.show()
    }

    override fun onError(message: String) {
        progress_bar_signup.hide()
        signup_root_layout.snackbar(message)
    }

    override fun onSuccess(user: User) {
        progress_bar_signup.hide()
    }
}