package com.mirkopruiti.android_team_test.ui.details

import android.content.Context
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mirkopruiti.android_team_test.data.db.FavoriteDao
import com.mirkopruiti.android_team_test.data.model.FavoritePokemon
import com.mirkopruiti.android_team_test.repository.DetailsRepository
import com.mirkopruiti.android_team_test.ui.details.state.DetailsState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.launch

class DetailsViewModel (private val detailsRepository: DetailsRepository, private val favoriteDao: FavoriteDao) : AndroidDataFlow(UIState.Empty) {

    fun getRemotePokemonInfo(name: String, context: Context)  = action (

        onAction = {
            val pokeInfo = detailsRepository.getPokemonInfo(name, context).asLiveData()
            setState(DetailsState.PokeInfo(pokeInfo))
        },
        onError = { error, _ -> setState {
            DetailsState.Error("Error to get Pokemons: $error")
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