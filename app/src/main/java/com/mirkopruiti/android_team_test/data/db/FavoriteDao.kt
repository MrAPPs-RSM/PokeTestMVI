package com.mirkopruiti.android_team_test.data.db

import androidx.paging.PagingSource
import androidx.room.*
import androidx.room.Delete
import com.mirkopruiti.android_team_test.data.model.FavoritePokemon


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(favouritePokemon: FavoritePokemon)

    @Query("SELECT EXISTS (SELECT 1 FROM FavoritePokemon WHERE id=:id)")
    suspend fun isFavorite(id: Int): Int

    @Query("SELECT * FROM FavoritePokemon")
    fun getPokemonLists(): PagingSource<Int, FavoritePokemon>

    @Delete
    suspend fun delete(favouritePokemon: FavoritePokemon)

}
