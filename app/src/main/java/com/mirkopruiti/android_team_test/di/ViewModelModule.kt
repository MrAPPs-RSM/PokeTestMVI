package com.mirkopruiti.android_team_test.di

import com.mirkopruiti.android_team_test.ui.details.DetailsViewModel
import com.mirkopruiti.android_team_test.ui.favorites.FavoriteViewModel
import com.mirkopruiti.android_team_test.ui.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { FavoriteViewModel(get(), get()) }
    viewModel { DetailsViewModel(get()) }

}