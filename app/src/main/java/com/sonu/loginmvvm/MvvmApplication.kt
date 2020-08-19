package com.sonu.loginmvvm

import android.app.Application
import com.sonu.loginmvvm.data.db.UserDatabase
import com.sonu.loginmvvm.data.network.MyApi
import com.sonu.loginmvvm.data.network.NetworkConnectionInterceptor
import com.sonu.loginmvvm.data.repositories.UserRepository
import com.sonu.loginmvvm.ui.auth.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MvvmApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MvvmApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton { UserDatabase(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
    }
}