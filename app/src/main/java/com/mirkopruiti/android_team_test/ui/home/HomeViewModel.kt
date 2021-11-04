package com.mirkopruiti.android_team_test.ui.home

import androidx.lifecycle.viewModelScope
import com.mirkopruiti.android_team_test.data.db.FavoriteDao
import com.mirkopruiti.android_team_test.data.model.FavoritePokemon
import com.mirkopruiti.android_team_test.repository.HomeRepository
import com.mirkopruiti.android_team_test.ui.home.state.HomeState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.launch


class HomeViewModel (private val homeRepository: HomeRepository, private val favoriteDao: FavoriteDao) : AndroidDataFlow(UIState.Empty) {

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

    fun getSearchPokemons(search: String) = action(
        onAction = {
            val pokemons = homeRepository.getSearchPokemonList(search)
            setState(HomeState.Pokemons(pokemons))
        },
        onError = { error, _ -> setState {
            HomeState.Error("Error to get Pokemons: $error")
        } }
    )

    fun setFavorites(isFavorite:Boolean, favoritePokemon:FavoritePokemon, completeCallback: () -> Any) {
        viewModelScope.launch() {
            if (isFavorite){
                favoriteDao.delete(favoritePokemon)
                completeCallback()
            } else {
                favoriteDao.insertPokemon(favoritePokemon)
                completeCallback()
            }
        }
    }

}