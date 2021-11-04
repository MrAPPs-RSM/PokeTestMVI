package com.mirkopruiti.android_team_test.ui.favorites

import androidx.lifecycle.viewModelScope
import com.mirkopruiti.android_team_test.data.db.FavoriteDao
import com.mirkopruiti.android_team_test.data.model.FavoritePokemon
import com.mirkopruiti.android_team_test.repository.FavoriteRepository
import com.mirkopruiti.android_team_test.ui.home.state.HomeState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.launch

class FavoriteViewModel (private val favoriteRepository: FavoriteRepository, private val favoriteDao: FavoriteDao) : AndroidDataFlow(UIState.Empty) {

    fun getFavoritePokemons() = action(
        onAction = {
            val pokemons = favoriteRepository.getFavoritePokemonList()
            setState(HomeState.Pokemons(pokemons))
        },
        onError = { error, _ -> setState {
            HomeState.Error("Error to get Favorite Pokemons: $error")
        } }
    )

    fun setFavorites(isFavorite:Boolean, favoritePokemon: FavoritePokemon, completeCallback: () -> Any) {
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