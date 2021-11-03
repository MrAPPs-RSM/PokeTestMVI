package com.mirkopruiti.android_team_test.di

import com.mirkopruiti.android_team_test.data.db.PokemonDatabase
import org.koin.dsl.module

val databaseModule = module {

    single { PokemonDatabase.getInstance(get()) }
    single { get<PokemonDatabase>().pokemonDao() }

}