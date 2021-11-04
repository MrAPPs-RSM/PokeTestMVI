package com.mirkopruiti.android_team_test.di

import com.mirkopruiti.android_team_test.repository.*
import org.koin.dsl.module

val repositoryModule = module {

    single { HomeRepository(get(), get(), get()) }
    single { HomeDataSource(get(), get(), get()) }
    single { LocalDataSource(get(), get()) }
    single { SearchDataSource(get()) }

    single { FavoriteRepository(get()) }
    single { FavoriteDataSource(get(), get()) }

    single { DetailsRepository(get(), get()) }

}