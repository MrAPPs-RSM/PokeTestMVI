package com.mirkopruiti.android_team_test.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.mirkopruiti.android_team_test.data.db.FavoriteDao
import com.mirkopruiti.android_team_test.data.db.PokemonDao
import com.mirkopruiti.android_team_test.data.model.Pokemon

const val F_STARTING_PAGE_INDEX = 0

class FavoriteDataSource(private val pokemonDao: PokemonDao, private val favoriteDao: FavoriteDao) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {

        val page = params.key ?: F_STARTING_PAGE_INDEX

        return try {
            val pokemons = pokemonDao.getPokemonList(page)
            for (poke in pokemons) {
                var favorite = favoriteDao.isFavorite(poke.id)
                poke.isFavorite = favorite == 1
            }
            pokemonDao.insertPokemonList(pokemons)
            LoadResult.Page(
                data = pokemons,
                prevKey = if (page == F_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (pokemons.isEmpty()) null else page + 1
            )

        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? = null
}
