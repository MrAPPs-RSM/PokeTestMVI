package com.mirkopruiti.android_team_test.ui.home.adapter

import com.mirkopruiti.android_team_test.data.model.Pokemon

interface PokemonClickListener {
    fun onPokemonClickListener(poke: Pokemon)
}