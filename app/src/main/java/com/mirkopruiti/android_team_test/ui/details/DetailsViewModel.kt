package com.mirkopruiti.android_team_test.ui.details

import android.content.Context
import androidx.lifecycle.asLiveData
import com.mirkopruiti.android_team_test.repository.DetailsRepository
import com.mirkopruiti.android_team_test.ui.details.state.DetailsState
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.data.UIState

class DetailsViewModel (private val detailsRepository: DetailsRepository) : AndroidDataFlow(UIState.Empty) {

    fun getRemotePokemonInfo(name: String, context: Context)  = action (

        onAction = {
            val pokeInfo = detailsRepository.getPokemonInfo(name, context).asLiveData()
            setState(DetailsState.PokeInfo(pokeInfo))
        },
        onError = { error, _ -> setState {
            DetailsState.Error("Error to get Pokemons: $error")
        } }
    )

}