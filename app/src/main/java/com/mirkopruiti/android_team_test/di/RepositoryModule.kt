package com.mirkopruiti.android_team_test.di

import com.mirkopruiti.android_team_test.repository.DetailsRepository
import com.mirkopruiti.android_team_test.repository.HomeDataSource
import com.mirkopruiti.android_team_test.repository.HomeRepository
import com.mirkopruiti.android_team_test.repository.LocalDataSource
import org.koin.dsl.module

val repositoryModule = module {

    single { HomeRepository(get(), get()) }
    single { HomeDataSource(get(), get()) }
    single { LocalDataSource(get()) }

    single { DetailsRepository(get(), get()) }

}