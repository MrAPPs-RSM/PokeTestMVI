package com.mirkopruiti.android_team_test.data.api

class ApiCall constructor(private val apiService: ApiService) {

    suspend fun fetchPokemonList(page: Int) = apiService.fetchPokemonList(
        limit = PAGING_SIZE,
        offset = page * PAGING_SIZE
    )

    suspend fun fetchPokemonInfo(name: String) = apiService.fetchPokemonInfo(
        name = name
    )

    companion object {
        private const val PAGING_SIZE = 100
    }
}