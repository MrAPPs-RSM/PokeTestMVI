package com.mirkopruiti.android_team_test.data.api

import com.mirkopruiti.android_team_test.data.model.PokemonInfo
import com.mirkopruiti.android_team_test.data.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    suspend fun fetchPokemonList(@Query("limit") limit: Int = 20, @Query("offset") offset: Int = 0): Response<PokemonResponse>

    @GET("pokemon/{name}")
    suspend fun fetchPokemonInfo(@Path("name") name: String): Response<PokemonInfo>
}