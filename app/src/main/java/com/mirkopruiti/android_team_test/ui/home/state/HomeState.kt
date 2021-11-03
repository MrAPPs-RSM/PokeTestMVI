package com.mirkopruiti.android_team_test.ui.home.state

import androidx.paging.PagingData
import com.mirkopruiti.android_team_test.data.model.Pokemon
import io.uniflow.core.flow.data.UIState
import kotlinx.coroutines.flow.Flow

sealed class HomeState : UIState(){
    object Init : HomeState()
    data class Pokemons(val pokemon: Flow<PagingData<Pokemon>>) : HomeState()
    data class Error(val error: String?) : HomeState()
}