package com.mirkopruiti.android_team_test.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mirkopruiti.android_team_test.data.db.PokemonDao
import com.mirkopruiti.android_team_test.data.model.Pokemon
import com.mirkopruiti.android_team_test.util.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository (private val homeDataSource: HomeDataSource, private val localDataSource: LocalDataSource, private val searchDataSource: SearchDataSource) {

    fun getRemotePokemonList(): Flow<PagingData<Pokemon>> {
            return Pager(
                config = PagingConfig(pageSize = PAGE_SIZE),
                pagingSourceFactory = { homeDataSource }
            ).flow
    }

    fun getLocalPokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { localDataSource }
        ).flow
    }

    fun getSearchPokemonList(search: String): Flow<PagingData<Pokemon>> {
        searchDataSource.search = search
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { searchDataSource }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}