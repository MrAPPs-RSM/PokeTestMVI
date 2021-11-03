package com.mirkopruiti.android_team_test.repository

import android.content.Context
import com.mirkopruiti.android_team_test.data.api.ApiCall
import com.mirkopruiti.android_team_test.data.db.PokemonDao
import com.mirkopruiti.android_team_test.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DetailsRepository (private val apiCall: ApiCall, private val pokemonDao: PokemonDao) {

    suspend fun getPokemonInfo(name: String, context: Context) = flow {
        val pokemonInfoLocal = pokemonDao.getPokemonInfo(name)
        if (pokemonInfoLocal == null) {
            if (NetworkUtil.isConnectedToNetwork(context)) {
                val pokemon = apiCall.fetchPokemonInfo(name).body()!!
                pokemonDao.insertPokemonInfo(pokemon)
                emit(pokemon)
            }
        } else{
            emit(pokemonInfoLocal)
        }
    }.flowOn(Dispatchers.IO)

}