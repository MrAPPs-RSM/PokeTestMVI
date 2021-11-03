package com.mirkopruiti.android_team_test.ui.details.state

import androidx.lifecycle.LiveData
import com.mirkopruiti.android_team_test.data.model.PokemonInfo
import io.uniflow.core.flow.data.UIState

sealed class DetailsState : UIState(){
    object Init : DetailsState()
    data class PokeInfo(val pokemonInfo: LiveData<PokemonInfo>) : DetailsState()
    data class Error(val error: String?) : DetailsState()
}