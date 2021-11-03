package com.mirkopruiti.android_team_test.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.HttpException
import com.mirkopruiti.android_team_test.data.api.ApiCall
import com.mirkopruiti.android_team_test.data.db.PokemonDao
import com.mirkopruiti.android_team_test.data.model.Pokemon
import java.io.IOException

const val STARTING_PAGE_INDEX = 0

class HomeDataSource(private val apiCall: ApiCall, private val pokemonDao: PokemonDao) : PagingSource<Int, Pokemon>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiCall.fetchPokemonList(page)
            val pokemons = response.body()?.results ?: emptyList()
            for (poke in pokemons) {
                poke.setPokemonId()
                poke.setPokemonPage(page)
            }
            pokemonDao.insertPokemonList(pokemons)
            LoadResult.Page(
                data = pokemons,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (pokemons.isEmpty()) null else page + 1
            )

        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? = null

//    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
}
