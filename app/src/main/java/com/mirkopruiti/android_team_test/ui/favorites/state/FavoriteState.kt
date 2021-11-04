package com.mirkopruiti.android_team_test.ui.favorites.state

import androidx.paging.PagingData
import com.mirkopruiti.android_team_test.data.model.Pokemon
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.Flow

sealed class FavoriteState : UIState(){
    object Init : FavoriteState()
    data class Pokemons(val pokemon: Flow<List<Pokemon>>) : FavoriteState()
    data class Error(val error: String?) : FavoriteState()
}