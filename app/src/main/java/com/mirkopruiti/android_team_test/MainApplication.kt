package com.mirkopruiti.android_team_test

import android.app.Application
import com.mirkopruiti.android_team_test.di.databaseModule
import com.mirkopruiti.android_team_test.di.networkModule
import com.mirkopruiti.android_team_test.di.repositoryModule
import com.mirkopruiti.kotlinpokemontest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(networkModule)
            modules(databaseModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }
}