package com.mirkopruiti.android_team_test.ui.home

import com.mirkopruiti.android_team_test.repository.HomeRepository
import com.mirkopruiti.android_team_test.ui.home.state.HomeState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState


class HomeViewModel (private val homeRepository: HomeRepository) : AndroidDataFlow(UIState.Empty) {

    fun getRemotePokemons() = action(
        onAction = {
            val pokemons = homeRepository.getRemotePokemonList()
            setState(HomeState.Pokemons(pokemons))
        },
        onError = { error, _ -> setState {
            HomeState.Error("Error to get Pokemons: $error")
        } }
    )

    fun getLocalPokemons() = action(
        onAction = {
            val pokemons = homeRepository.getLocalPokemonList()
            setState(HomeState.Pokemons(pokemons))
        },
        onError = { error, _ -> setState {
            HomeState.Error("Error to get Pokemons: $error")
        } }
    )

}