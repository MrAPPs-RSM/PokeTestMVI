package com.mirkopruiti.android_team_test.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mirkopruiti.android_team_test.data.model.Pokemon
import kotlinx.coroutines.flow.Flow

class FavoriteRepository (private val favoriteDataSource: FavoriteDataSource) {

    fun getFavoritePokemonList(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { favoriteDataSource }
        ).flow
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}